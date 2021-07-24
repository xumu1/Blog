文章引用自：https://blog.csdn.net/zhangskd/article/details/16986931

仅做个人学习，请支持原创

SYN Flood


下面这段介绍引用自[1].

SYN Flood是一种非常危险而常见的Dos攻击方式。到目前为止，能够有效防范SYN Flood攻击的手段并不多，

SYN Cookie就是其中最著名的一种。



SYN Flood攻击是一种典型的拒绝服务(Denial of Service)攻击。所谓的拒绝服务攻击就是通过进行攻击，使受害主机或

网络不能提供良好的服务，从而间接达到攻击的目的。

SYN Flood攻击利用的是IPv4中TCP协议的三次握手(Three-Way Handshake)过程进行的攻击。

TCP服务器收到TCP SYN request包时，在发送TCP SYN + ACK包回客户机前，TCP服务器要先分配好一个数据区专门

服务于这个即将形成的TCP连接。一般把收到SYN包而还未收到ACK包时的连接状态称为半打开连接(Half-open Connection)。

在最常见的SYN Flood攻击中，攻击者在短时间内发送大量的TCP SYN包给受害者。受害者(服务器)为每个TCP SYN包分配

一个特定的数据区，只要这些SYN包具有不同的源地址(攻击者很容易伪造)。这将给TCP服务器造成很大的系统负担，最终

导致系统不能正常工作。



SYN Cookie


SYN Cookie原理由D.J. Bernstain和Eric Schenk提出。

SYN Cookie是对TCP服务器端的三次握手做一些修改，专门用来防范SYN Flood攻击的一种手段。它的原理是，在TCP服务器

接收到TCP SYN包并返回TCP SYN + ACK包时，不分配一个专门的数据区，而是根据这个SYN包计算出一个cookie值。这个

cookie作为将要返回的SYN ACK包的初始序列号。当客户端返回一个ACK包时，根据包头信息计算cookie，与返回的确认序列

号(初始序列号 + 1)进行对比，如果相同，则是一个正常连接，然后，分配资源，建立连接。



实现的关键在于cookie的计算，cookie的计算应该包含本次连接的状态信息，使攻击者不能伪造。

cookie的计算：

服务器收到一个SYN包，计算一个消息摘要mac。

mac = MAC(A, k);

MAC是密码学中的一个消息认证码函数，也就是满足某种安全性质的带密钥的hash函数，它能够提供cookie计算中需要的安全性。

在Linux实现中，MAC函数为SHA1。

A = SOURCE_IP || SOURCE_PORT || DST_IP || DST_PORT || t || MSSIND

k为服务器独有的密钥，实际上是一组随机数。

t为系统启动时间，每60秒加1。

MSSIND为MSS对应的索引。



实现


（1）启用条件

判断是否使用SYN Cookie。如果SYN Cookie功能有编译进内核(CONFIG_SYN_COOKIE)，且选项

tcp_syncookies不为0，那么可使用SYN Cookie。同时设置SYN Flood标志(listen_opt->synflood_warned)。

/* Return true if a syncookie should be sent. */
bool tcp_syn_flood_action(struct sock *sk, const struct sk_buff *skb, const char *proto)
{
const char *msg = "Dropping request";
bool want_cookie = false;
struct listen_sock *lopt;

#ifdef CONFIG_SYN_COOKIE
    if (sysctl_tcp_syncookies) { /* 如果允许使用SYN Cookie */
        msg = "Sending cookies";
        want_cookie = true;
        NET_INC_STATS_BH(sock_net(sk), LINUX_MIB_TCPREQQFULLDOCOOKIES);
    } else
#endif
        NET_INC_STATS_BH(sock_net(sk), LINUX_MIB_TCPREQQFULLDROP);
 
    lopt = inet_csk(sk)->icsk_accept_queue.listen_opt; /* 半连接队列 */
 
    if (! lopt->synflood_warned) {
        lopt->synflood_warned = 1; /* 设置SYN Flood标志 */
        pr_info("%s: Possible SYN flooding on port %d. %s.  Check SNMP counters.\n",
                       proto, ntohs(tcp_hdr(skb)->dest), msg);
    }
 
    return want_cookie;
}


（2）生成cookie

计算SYN Cookie的值。

函数调用路径：

tcp_v4_conn_request

        |--> cookie_v4_init_sequence

                          |--> secure_tcp_syn_cookie

/* Generate a syncookie. mssp points to the mss, which is returned rounded down to the
* value encoded in the cookie.
  */

__u32 cookie_v4_init_sequence(struct sock *sk, struct sk_buff *skb, __u16 *mssp)
{
const struct iphdr *iph = ip_hdr(skb);
const struct tcphdr *th = tcp_hdr(skb);
int mssind; /* mss index */
const __u16 mss = *mssp;

    tcp_synq_overflow(sk); /* 记录半连接队列溢出的最近时间 */
 
    for (mssind = ARRAY_SIZE(msstab) - 1; mssind; mssind--)
        if (mss >= msstab[mssind])
            break;
    *mssp = msstab[mssind];
 
    NET_INC_STATS_BH(sock_net(sk), LINUX_MIB_SYNCOOKIESSENT);
 
    return secure_tcp_syn_cookie(iph->saddr, iph->daddr, th->source, th->dest, ntohl(th->seq),
                      jiffies / (HZ * 60), mssind); /* 计算SYN Cookie的具体值 */
}
/* syncookie: remember time of last synqueue overflow */
static inline void tcp_synq_overflow(struct sock *sk)
{
tcp_sk(sk)->rx_opt.ts_recent_stamp = jiffies;
}

/*
* MSS Values are taken from the 2009 paper
* 'Measuring TCP Maximum Segment Size' by S. Alcock and R. Nelson:
* - values 1440 to 1460 accounted for 80% of observed mss values
* - values outside the 536-1460 range are rare (<0.2%).
*
* Table must be sorted.
  */
  static __u16 const msstab[] = {
  64,
  512,
  536,
  1024,
  1440,
  1460,
  4312,
  8960,
  };
  static __u32 secure_tcp_syn_cookie(__be32 saddr, __be32 daddr, __be16 sport, __be16 dport,
  __u32 sseq, __u32 count, __u32 data)
  {
  /* Compute the secure sequence number.
    * The output should be:
    * HASH(sec1, saddr, sport, daddr, dport, sec1) + sseq + (count * 2^24) +
    *     (HASH(sec2, saddr, sport, daddr, dport, count, sec2) % 2^24).
    * Where sseq is their sequence number and count increases every minute by 1.
    * As an extra hack, we add a small "data" value that encodes the MSS into the second hash value.
      */
      return (cookie_hash(saddr, daddr, sport, dport, 0, 0) + sseq + (count << COOKIEBITS) +
      ((cookie_hash(saddr, daddr, sport, dport, count, 1) + data) & COOKIEMASK));

}

#define COOKIEBITS 24 /* Upper bits store count */
#define COOKIEMASK (((__u32) 1 << COOKIEBITS) - 1)
#define SHA_DIGEST_WORDS 5
#define SHA_WORKSPACE_WORDS 16
服务器的密钥、SHA1计算。

__u32 syncookie_secret[2] [16 - 4 + SHA_DIGEST_WORDS];

static __init int init_syncookies(void)
{
get_random_bytes(syncookie_secret, sizeof(syncookie_secret));
return 0;
}

static DEFINE_PER_CPU(__u32 [16 + 5 + SHA_WORKSPACE_WORDS], ipv4_cookie_scratch);

static u32 cookie_hash(__be32 saddr, _be32 daddr, __be16 sport, __be16 dport, u32 count, int c)
{
__u32 *tmp = __get_cpu_var(ipv4_cookie_scratch);

    memcpy(tmp + 4, syncookie_secret[c], sizeof(syncookie_secret[c])); /* c取值为0、1 */
    tmp[0] = (__force u32) saddr;
    tmp[1] = (__force u32) daddr;
    tmp[2] = ((__force u32) sport << 16) + (__force u32) dport;
    tmp[3] = count;
 
    sha_transform(tmp + 16, (__u8 *)tmp, tmp + 16 + 5); /* generate a 160-bit digest from 512-bit block */
    return tmp[17];
}
SHA1

安全哈希算法(Secure HASH Algorithm)主要适用于数字签名。

对于长度小于2^64位的消息，SHA1会产生一个160位的消息摘要。当接收到消息的时候，这个消息摘要可以用来

验证数据的完整性。在传输的过程中，数据可能会发生变化，那么这时候就会产生不同的消息摘要。

SHA1有如下特性：

1. 不可以从消息摘要中复原信息。

2. 两个不同的消息不会产生同样的消息摘要。

在Git中，也使用SHA1来标识每一次提交。

/* sha_transform - single block SHA1 transform
* @digest: 160 bit digest to update
* @data: 512 bits of data to hash
* @array: 16 words of workspace (see note)
*
* This function generates a SHA1 digest for a single 512-bit block.
* /
  void sha_transform(__u32 *digest, const char *data, __u32 *array) {}


（3）保存TCP选项信息

tcp_v4_send_synack

        |--> tcp_make_synack

                       |--> cookie_init_timestamp

如果SYNACK段使用SYN Cookie，并且使用时间戳选项，则把TCP选项信息保存在SYNACK段中tsval的低6位。

/* When syncookies are in effect and tcp timestamps are enabled we encode tcp options
* in the lower bits of the timestamp value that will be sent in the syn-ack.
* Since subsequent timestamps use the normal tcp_time_stamp value, we must make
* sure that the resulting initial timestamp is <= tcp_time_stamp.
  */
  __u32 cookie_init_timestamp(struct request_sock *req)
  {
  struct inet_request_sock *ireq;
  u32 ts, ts_now = tcp_time_stamp;
  u32 options = 0;
  ireq = inet_rsk(req);

  options = ireq->wscale_ok ? ireq->snd_wscale : 0xf;
  options |= ireq->sack_ok << 4;
  options |= ireq->ecn_ok << 5;

  ts = ts_now & ~TSMASK;
  ts |= options;

  if (ts > ts_now) {
  ts >>= TSBITS;
  ts--;
  ts <<= TSBITS;
  ts |= options;
  }
  return ts;
  }

#define TSBITS 6
#define TSMASK (((__u32) 1 << TSBITS) - 1)

（4）验证cookie

函数调用路径：

tcp_v4_hnd_req

        |--> cookie_v4_check

                      |--> cookie_check

                                       |--> check_tcp_syn_cookie



SYN Cookie的设计非常巧妙， 我们来看看它是怎么验证的。

首先，把ACK包的ack_seq - 1，得到原来计算的cookie。把ACK包的seq - 1，得到SYN段的seq。

cookie的计算公式为：

cookie = cookie_hash(saddr, daddr, sport, dport, 0, 0) + seq +

                (t1 << 24) + (cookie_hash(saddr, daddr, sport, dport, t1, 1) + mssind) % 24;

t1为服务器发送SYN Cookie的时间，单位为分钟，保留在高12位。

mssind为MSS的索引(0 - 7)，保留在低24位。



现在可以反过来求t1：

t1 = (cookie - cookie_hash(saddr, daddr, sport, dport, 0, 0) - seq) >> 24; /* 高12位表示时间 */

t2为收到ACK的时间，t2 - t1 < 4分钟，才是合法的。也就是说ACK必须在4分钟内到达才行。



验证完时间后，还需验证mssind：

cookie -= (cookie_hash(saddr, daddr, sport, dport, 0, 0) - seq);

mssind = (cookie - cookie_hash(saddr, daddr, sport, dport, t1, 1)) % 24; /* 低24位 */

mssind < 8，才是合法的。



如果t1和mssind都是合法的，则认为此ACK是合法的，可以直接完成三次握手。

/* Check if a ack sequence number is a valid syncookie.
* Return the decoded mss if it is, or 0 if not.
  */

static inline int cookie_check(struct sk_buff *skb, __u32 cookie)
{
const struct iphdr *iph = ip_hdr(skb);
const struct tcphdr *th = tcp_hdr(skb);
__u32 seq = ntohl(th->seq) - 1; /* SYN的序号 */

    __u32 mssind = check_tcp_syn_cookie(cookie, iph->saddr, iph->daddr, th->source, th->dest,
                          seq, jiffies / (HZ * 60), COUNTER_TRIES);
 
    /* 如果不合法则返回0 */
    return mssind < ARRAY_SIZE(msstab) ? msstab[mssind] : 0;
}
/* 使用SYN Cookie时，ACK超过了这个时间到达，会被认为不合法。*/
/* This (misnamed) value is the age of syncookie which is permitted.
* Its ideal value should be dependent on TCP_TIMEOUT_INIT and sysctl_tcp_retries1.
* It's a rather complicated formula (exponential backoff) to compute at runtime so it's
* currently hardcoded here.
  */
#define COUNTER_TRIES 4 /* 4分钟 */

static __u32 check_tcp_syn_cookie(__u32 cookie, __be32 saddr, __be32 daddr, __be16 sport,
__be16 dport, __u32 sseq, __u32 count, __u32 maxdiff)
{
__u32 diff;

    /* Strip away the layers from the cookie, 剥去固定值的部分 */
    cookie -= cookie_hash(saddr, daddr, sport, dport, 0, 0) + sseq;
 
    /* Cookie is now reduced to (count * 2^24) + (hash % 2^24) */
    diff = (count - (cookie >> COOKIEBITS)) & ((__u32) -1 >> COOKIEBITS); /* 高12位是时间，单位为分钟 */
    if (diff >= maxdiff)
        return (__u32)-1;
 
    /* Leaving the data behind，返回的是原来的data，即mssind */
    return (cookie - cookie_hash(saddr, daddr, sport, dport, count - diff, 1)) & COOKIEMASK;
}


（5）建立连接

接收到ACK后，SYN Cookie的处理函数为cookie_v4_check()。

首先要验证cookie是否合法。

如果cookie是不合法的，返回监听sk，会导致之后发送一个RST给客户端。

如果cookie是合法的，则创建和初始化连接请求块。接着为新的连接创建和初始化一个新的传输控制块，

把它和连接请求块关联起来，最后把该连接请求块链入全连接队列中，等待accept()。



时间戳对SYN Cookie有着重要的意义，如果不支持时间戳选项，则通过SYN Cookie建立的连接就会

不支持大多数TCP选项。

struct sock *cookie_v4_check(struct sock *sk, struct sk_buff *skb, struct ip_options *opt)
{
struct tcp_options_received tcp_opt;
const u8 *hash_location;
struct inet_request_sock *ireq;
struct tcp_request_sock *treq;
struct tcp_sock *tp = tcp_sk(sk);
const struct tcphdr *th = tcp_hdr(skb);
__u32 cookie = ntohl(th->ack_seq) - 1;
struct sock *ret = sk;
struct request_sock *req;
int mss;
struct rtable *rt;
__u8 rcv_wscale;
bool ecn_ok = false;
struct flowi4 fl4;

    if (! sysctl_tcp_syncookies || ! th->ack || th->rst)
        goto out;
 
    /* 验证cookie的合法性，必须同时符合：
     * 1. 最近3s内有发生半连接队列溢出。
     * 2. 通过cookie反算的t1和mssind是合法的。
     */
    if (tcp_synq_no_recent_overflow(sk) || (mss = cookie_check(skb, cookie)) == 0) {
        NET_INC_STATS_BH(sock_net(sk), LINUX_MIB_SYNCOOKIESFAILED);
        goto out;
    }
    NET_INC_STATS_BH(sock_net(sk), LINUX_MIB_SYNCOOKIESRECV);
 
    /* check for timestamp cookie support */
    memset(&tcp_opt, 0, sizeof(tcp_opt));
 
    /* 全面解析TCP选项，并保存到tcp_opt中 */
    tcp_parse_options(skb, &tcp_opt, &hash_location, 0, NULL);
 
    /* 如果有使用时间戳选项，则从ACK的tsecr中提取选项信息 */
    if (! cookie_check_timestamp(&tcp_opt, &ecn_ok))
        goto out;
 
    ret = NULL;
    /* 从缓存块中分配一个request_sock实例，指定此实例的操作函数集为tcp_request_sock_ops */
    req = inet_reqsk_alloc(&tcp_request_sock_ops);
    if (! req)
        goto out;
 
    ireq = inet_rsk(req);
    treq = tcp_rsk(req);
    treq->rcv_isn = ntohl(th->seq) - 1; /* 客户端的初始序列号 */
    treq->snt_isn = cookie; /* 本端的初始序列号 */
    req->mss = mss; /* 客户端通告的MSS，通过解析cookie获得 */
    ireq->loc_port = th->dest; /* 本端端口 */
    ireq->rmt_port = th->source; /* 客户端端口 */
    ireq->loc_addr = ip_hdr(skb)->daddr; /* 本端IP */
    ireq->rmt_addr = ip_hdr(skb)->saddr; /* 客户端IP */
    ireq->ecn_ok = ecn_ok; /* ECN选项，通过TS编码获得 */
    ireq->snd_wscale = tcp_opt.snd_wscale; /* 客户端窗口扩大因子，通过TS编码获得 */
    ireq->sack_ok = tcp_opt.sack_ok; /* SACK允许选项，通过TS编码获得 */
    ireq->wscale_ok = tcp_opt.wscale_ok; /* 窗口扩大选项，通过TS编码获得 */
    ireq->tstamp_ok = tcp_opt.saw_tstamp; /* 时间戳选项，通过观察ACK段有无携带时间戳 */
    req->ts_recent = tcp_opt.saw_tstamp ? tcp_opt.rcv_tsval : 0; /* 本端下个发送段的时间戳回显值 */
    treq->snt_synack = tcp_opt.saw_tstamp ? tcp_opt.rcv_tsecr : 0; /* 本端发送SYNACK段的时刻 */
 
    /* We throwed the options of the initial SYN away, so we hope the ACK carries the same options
     * again (see RFC1122 4.2.3.8)
     * 通过ACK段，获取IP选项。
     */
    if (opt && opt->optlen) {
        int opt_size = sizeof(struct ip_options_rcu) + opt->optlen;
        ireq->opt = kmalloc(opt_size, GFP_ATOMIC);
 
        if (ireq->opt != NULL && ip_options_echo(&ireq->opt->opt, skb)) {
            kfree(ireq->opt);
            ireq->opt = NULL;
        }
    }
 
    /* SELinux相关 */
    if (security_inet_conn_request(sk, skb, req)) {
        reqsk_free(req);
        goto out;
    }
 
    req->expires = 0UL; /* SYNACK的超时时间 */
    req->retrans = 0; /* SYNACK的重传次数 */
 
    /* We need to lookup the route here to get at the correct window size.
     * We should better make sure that the window size hasn't changed since we
     * received the original syn, but I see no easy way to do this.
     * 查找路由缓存。
     */
    flowi4_init_output(&fl4, 0, sk->sk_mark, RT_CONN_FLAGS(sk), RT_SCOPE_UNIVERSE,
        IPPROTO_TCP, inet_sk_flowi_flags(sk), (opt && opt->srr) ? opt->faddr : ireq->rmt_addr,
        ireq->loc_addr, th->source, th->dest);
    security_req_classify_flow(req, flowi4_to_flowi(&fl4));
    rt = ip_route_output_key(sock_net(sk), &fl4);
    if (IS_ERR(rt)) {
        reqsk_free(req);
        goto out;
    }
 
    /* Try to redo what tcp_v4_send_synack did. */
    req->window_clamp = tp->window_clamp ? : dst_metric(&rt->dst, RTAX_WINDOW);
 
    /* 获取接收窗口的初始值，窗口扩大因子和接收窗口的上限 */
    tcp_select_initial_window(tcp_full_space(sk), req->mss, &req->rcv_wnd, &req->window_clamp,
        ireq->wscale_ok, &rcv_wscale, dst_metric(&rt->dst, RTAX_INITRWND));
    ireq->rcv_wscale = rcv_wscale;
 
    /* 到了这里，三次握手基本完成。
     * 接下来为新的连接创建和初始化一个传输控制块，并把它和连接请求块关联起来。
     * 最后把该连接请求块移入全连接队列中，等待accept()。
     */
    ret = get_cookie_sock(sk, skb, req, &rt->dst);    
 
    /* ip_queue_xmit() depends on our flow being setup
     * Normal sockets get it right from inet_csk_route_child_sock()
     */
    if (ret)
        inet_sk(ret)->cork.fl.u.ip4 = fl4;

out:
return ret;
}

/* RFC 1122 initial RTO value, now used as a fallback RTO for the initial data
* transmssion if no valid RTT sample has been accquired, most likely due to
* retrans in 3WHS.
  */
#define TCP_TIMEOUT_FALLBACK ((unsigned) (3 * HZ))

/* syncookies: no recent synqueue overflow on this listening socket?
* 如果最近3s内没有发生半连接队列溢出，则为真。
  */
  static inline bool tcp_synq_no_recent_overflow(const struct sock *sk)
  {
  unsigned long last_overflow = tcp_sk(sk)->rx_opt.ts_recent_stamp;
  return time_after(jiffies, last_overflow + TCP_TIMEOUT_FALLBACK);
  }


如果SYNACK段使用SYN Cookie，并且使用时间戳选项，则把TCP选项信息保存在SYNACK段中tsval的低6位。

所以，现在收到ACK后，可以从ACK段的tsecr中提取出这些选项。

/* When syncookies are in effect and tcp timestamps are enabled we stored addtional tcp
* options in the timestamp.
* This extracts these options from the timestamp echo.
* The lowest 4 bits store snd_wscale.
* next 2 bits indicate SACK and ECN support.
* return false if we decode an option that should not be.
  */
  bool cookie_check_timestamp(struct tcp_options_received *tcp_opt, bool *ecn_ok)
  {
  /* echoed timestamp, lowest bits contain options */
  u32 options = tcp_opt->rcv_tsecr & TSMASK;

  /* 如果ACK没有携带时间戳，则把tcp_opt中的tstamp_ok、sack_ok、wscale_ok
    * snd_wscale和cookie_plus置零。
      */
      if (! tcp_opt->saw_tstamp) {
      tcp_clear_options(tcp_opt);
      return true;
      }

  if (! sysctl_tcp_timestamps)
  return false;

  tcp_opt->sack_ok = (options & (1 << 4)) ? TCP_SACK_SEEN : 0;
  *ecn_ok = (options >> 5) & 1;

  if (*ecn_ok && ! sysctl_tcp_ecn)
  return false;

  if (tcp_opt->sack_ok && ! sysctl_tcp_sack)
  return false;

  if ((options & 0xf) == 0xf)
  return true; /* no window scaling. */

  tcp_opt->wscale_ok = 1;
  tcp_opt->snd_wscale = options & 0xf;
  return sysctl_tcp_window_scaling != 0;
  }


为新的连接创建和初始化一个传输控制块，然后把完成三次握手的req和新sock关联起来，

并把该连接请求块移入全连接队列中。

static inline struct sock *get_cookie_sock(struct sock *sk, struct sk_buff *skb,
struct request_sock *req, struct dst_entry *dst)
{
struct inet_connection_sock *icsk = inet_csk(sk);
struct sock *child;

    /* 为新的连接创建和初始化一个传输控制块。
     * 对于TCP/IPv4，实例为ipv4_specific，调用tcp_v4_syn_recv_sock()
     */
    child = icsk->icsk_af_ops->syn_recv_sock(sk, skb, req, dst);
 
    if (child)
        /* 把完成三次握手的连接请求块，和新的sock关联起来，并把它移入全连接队列中。*/
        inet_csk_reqsk_queue_add(sk, req, child); 
    else
        reqsk_free(req);
 
    return child;
}

static inline void inet_csk_reqsk_queue_add(struct sock *sk, struct request_sock *req, struct sock *child)
{
reqsk_queue_add(&inet_csk(sk)->icsk_accept_queue, req, sk, child);
}


把完成三次握手的连接请求块，和新的sock关联起来，并把它移入全连接队列中，等待被accept()。

static inline void reqsk_queue_add(struct request_sock_queue *queue, struct request_sock *req,
struct sock *parent, struct sock *child)
{
req->sk = child; /* 连接请求块request_sock，关联了一个新sock */
sk_acceptq_added(parent); /* 监听sock的全连接队列中的连接请求个数加一 */

    /* 全连接队列是一个FIFO队列，把req加入到队列尾部 */
    if (queue->rskq_accept_head == NULL)
        queue->rskq_accept_head = req;
    else
        queue->rskq_accept_tail->dl_next = req;
 
    queue->rskq_accept_tail = req;
    req->dl_next = NULL;
}

static inline void sk_acceptq_added(struct sock *sk)
{
sk->sk_ack_backlog++;
}


评价


SYN Cookie技术由于在建立连接的过程中不需要在服务器端保存任何信息，实现了无状态的三次握手，从而有效的

防御了SYN Flood攻击。但是该方法也存在一些弱点。由于cookie的计算只涉及到包头部分信息，在建立连接的过程

中不在服务器端保存任何信息，所以失去了协议的许多功能，比如超时重传。此外，由于计算cookie有一定的运算量，

增加了连接建立的延迟时间，因此，SYN Cookie技术不能作为高性能服务器的防御手段。通常采用动态资源分配机制，

当分配了一定的资源后再采用cookie技术，Linux就是这样实现的。还有一个问题是，当我们避免了SYN Flood攻击的

同时，也提供了另一种拒绝服务攻击方式，攻击者发送大量的ACK报文，服务器忙于计算验证。尽管如此，在预防

SYN Flood供给方面，SYN Cookie技术仍然是有效的（引用自[1]）。



扩展


Linux内核中的SYN Cookie机制主要的功能是防止本机遭受SYN Flood攻击。

SYN Cookie Firewall利用SYN Cookie的原理，在内网和外网之间实现TCP三次握手过程的代理(proxy)。

一些SYN攻击的防火墙也是基于SYN Cookie，只是把这个功能移动到内核之外的代理服务器上。



Reference


[1]. https://www.ibm.com/developerworks/cn/linux/l-syncookie/


————————————————
版权声明：本文为CSDN博主「zhangskd」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/zhangskd/article/details/16986931