import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.Locale;

/**
 * Description:
 * date: 2021-1-21
 *
 * @author xumu
 */
public class testSort {
    public static void main(String[] args) {
        int[] test = new int[]{1,2,2,1,4,1,2,55,547,5};
        quickSort(test,0,test.length-1);
        System.out.println(1);
    }

    private static void quickSort(int[] nums,int l,int r){
        if (l<r){
            int mid = partition(nums,l,r);
            quickSort(nums,l,mid-1);
            quickSort(nums,mid+1,r);
        }
    }
    private static int partition(int[] nums,int l,int r ){
        int target = nums[r];
        int little = l-1;
        for (int i = l; i < r; i++) {
            if (nums[i] < target){
                little++;
                swap(nums,little,i);
            }
        }
        little++;
        swap(nums,little,r);
        return little;
    }
    private static void swap(int[] nums, int l,int r){
        int tmp = nums[l];
        nums[l] = nums[r];
        nums[r] = tmp;
    }

}
