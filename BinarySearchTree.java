package Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
이전처럼 HashMap에 노드에 대한 정보를 담았지만 시간초과가 나왔다.
기존처럼 하되 최상위 루트 노드를 대상으로 트리를 만들어 나간 후
최상위 루트 노드를 토대로 후위순회를 한다
 */

public class BinarySearchTree {

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int top_root = 0;
        TreeNode root_tn = null;//최상위 노드의 데이터를 토대로 트리를 이어 나가기 위한 데이터
        while(true){
            String s = br.readLine();
            if(s == null || s.equals("")){
                br.close();
                break;
            }

            int num = Integer.parseInt(s);

            if(top_root == 0){//최상위 노드 세팅
                top_root = num;
                root_tn = new TreeNode(num, null, null);
            }else{//그 외 자식노드 설정
                set_tree(root_tn, num);
            }
        }

        //후위 순회 메서드로 이동
        postorder(root_tn);
        System.out.println(sb);
    }

    public static void postorder(TreeNode root_tn){
        //후위순회 : 왼쪽 -> 오른쪽 -> 루트 순
        if(root_tn.left != null) {
            //왼쪽 자식이 있다면 재귀함수를 호출
            postorder(root_tn.left);
        }

        if(root_tn.right != null){
            //오른족 자식이 있다면 재귀함수를 호출
            postorder(root_tn.right);
        }

        sb.append(root_tn.node);//루트를 붙여준다
        sb.append("\n");
    }

    //전위순회 결과를 트리로 세팅하는 메소드
    public static void set_tree(TreeNode root_tn, int num){
        if(root_tn.node > num){//삽입하려는 숫자가 노드보다 큰 경우
            if(root_tn.left == null){
                //왼쪽 자식이 없는 경우 그 자리에 삽입한다
                root_tn.left = new TreeNode(num, null, null);
            }else{
                //왼쪽에 자식이 있는 경우 루트를 바꿔 다시 탐색한다
                set_tree(root_tn.left, num);
            }
        }else{//삽입하는 숫자가 노드보다 작은 경우
            if(root_tn.right == null){
                //오른쪽 자식이 없는 경우 그 자리에 삽입한다
                root_tn.right = new TreeNode(num, null, null);
            }else{
                //오른쪽에 자식이 있는 경우 루트를 바꿔 다시 탐색한다
                set_tree(root_tn.right, num);
            }
        }
    }

}

class TreeNode{
    int node;//해당 노드에 대한 번호
    TreeNode left;//왼쪽 노드
    TreeNode right;//오른쪽 노드

    public TreeNode(int node, TreeNode left, TreeNode right){
        this.node = node;
        this.left = left;
        this.right = right;
    }
}
