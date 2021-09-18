package Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/*
노드의 개수, 중위식 표기, 후위식 표기가 주어질 때 해당 트리의 전위식 표현은 어떻게 되는가?
맨 처음에는 후위식 표기법 맨 마지막 숫자가 최상위 루트노드임
후위 리스트의 루트 인덱스를 토대로 중위 인덱스의 기준을 잡고 왼쪽 오른쪽으로 나눠서 확인해본다
나눠진 중위 리스트를 탐색을 하는데 후위 리스트에 가장 오른쪽에 있는 수가 루트가 되고 이를 다시 기준으로 잡아 왼족 오른쪽 나눠 탐색한다
 */
public class SearchTree2 {

    static int[] postorder_arr;//후위표기식으로 주어진 인덱스
    static int[] inorder_arr;//중위표기식으로 주어진 인덱스
    static ArrayList<Integer> inorder_list = new ArrayList<>();//주어진 중위표기식을 리스트로 표현
    static ArrayList<Integer> postorder_list = new ArrayList<>();//주어진 후위표기식을 리스트로 표현
    static HashMap<Integer, TreeNodeData> tree = new HashMap<>();//트리를 Map으로 표현
    static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        postorder_arr = new int[n + 1];
        inorder_arr = new int[n + 1];

        for(int i = 1; i <= n; i++){
            tree.put(i, null);
        }

        String[] inorder = br.readLine().split(" ");

        //중위 표기식에 대한 정보들을 담는다
        int inorder_idx = 0;
        for(String s : inorder){
            inorder_list.add(Integer.parseInt(s));
            inorder_arr[Integer.parseInt(s)] = inorder_idx;//해당 수에 대한 인덱스를 담는다
            inorder_idx++;
        }

        String[] postorder = br.readLine().split(" ");

        //후위 표기식에 대한 정보를 담는다
        int postorder_idx = 0;
        for(String s : postorder){
            postorder_list.add(Integer.parseInt(s));
            postorder_arr[Integer.parseInt(s)] = postorder_idx;
            postorder_idx++;
        }

        int root = Integer.parseInt(postorder[n - 1]);//후위표기식의 맨 오른쪽 수가 루트가 된다
        int index = inorder_arr[root];//중위표기식의 기준이 되는 인덱스

        int left = search_left(0,index - 1);//왼쪽 노드 값 구하기
        int right = search_right(index + 1, n - 1);//오른쪽 노드 값 구하기
        tree.put(root, new TreeNodeData(left, right));//루트에 왼쪽값과 오른쪽 값을 삽입

        //전위표기식
        answer.append(root);
        answer.append(" ");
        preorder(tree.get(root).left);
        preorder(tree.get(root).right);

        System.out.println(answer);
    }

    public static void preorder(int root){
        if(tree.get(root) == null){//비어있으면 더이상 탐색을 진행하지 안흔ㄴ다
            return;
        }

        //순서는 루트 -> 왼쪽 -> 오른쪽 순
        answer.append(root);
        answer.append(" ");
        int left = tree.get(root).left;
        int right = tree.get(root).right;

        if(left > 0){
            preorder(tree.get(root).left);
        }

        if(right > 0){
            preorder(tree.get(root).right);
        }
    }

    public static int search_left(int start, int end){
        if(start > end){//탐색할 부분이 없는 경우 0을 반환
            return 0;
        }

        //for문을 돌면서 후위 표기법의 인덱스가 큰 수가 루트가 된다
        int num = 0;
        int max_index = -1;
        for(int i =  start; i <= end; i++){//기준을 잡는 인덱스 찾기
            //postorder_list.indexOf(inorder_list.get(i))
            if(postorder_arr[inorder_list.get(i)] > max_index){
                max_index = postorder_arr[inorder_list.get(i)];
                num = inorder_list.get(i);
            }
        }

        //루트에서 왼쪽 오른족을 확인
        int left = search_left(start, inorder_arr[num] - 1);
        int right = search_right(inorder_arr[num] + 1, end);
        tree.put(num, new TreeNodeData(left, right));

        return num;//구한 루트를 반환
    }

    public static int search_right(int start, int end){
        if(start > end){//탐색할 부분이 없는 경우 0을 반환
            return 0;
        }

        //for문을 돌면서 후위 표기법의 인덱스가 큰 수가 루트가 된다
        int num = 0;
        int max_index = -1;
        for(int i =  start; i <= end; i++){
            //postorder_list.indexOf(inorder_list.get(i))
            if(postorder_arr[inorder_list.get(i)] > max_index){
                max_index = postorder_arr[inorder_list.get(i)];
                num = inorder_list.get(i);
            }
        }

        //루트에서 왼쪽 오른족을 확인
        int left = search_left(start, inorder_arr[num] - 1);
        int right = search_right(inorder_arr[num] + 1, end);
        tree.put(num, new TreeNodeData(left, right));

        return num;//구한 루트를 반환
    }
}

//트리에 대한 클래스
class TreeNodeData{
    int left;
    int right;

    public TreeNodeData(int left, int right){
        this.left = left;
        this.right = right;
    }
}
