package Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
전반적인 트리 순회를 작성하는 알고리즘
전위순회 : 루트 -> 왼쪽 -> 오른쪽
중위순회 : 왼쪽자식 -> 루트 -> 오른쪽
후위순회 : 왼쪽 -> 오른쪽 -> 루트
 */

public class SearchTree {

    static HashMap<String, String[]> tree = new HashMap<>();
    static StringBuilder pre = new StringBuilder();//전위순회 결과
    static StringBuilder ino = new StringBuilder();//중위순회 결과
    static StringBuilder post = new StringBuilder();//후위순회 결과

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        char c = 'A';//루트노드의 문자가 될 A
        //노드의 종류를 하나하나 만든다
        for(int i = 0; i < n; i++){
            String key = "" + c;
            String[] value = new String[2];
            tree.put(key, value);
            c++;
        }

        for(int i = 0; i < n; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String parent = st.nextToken();//루트노드
            String left = st.nextToken();//왼쪽자식
            String right = st.nextToken();//오른쪽 자식
            String[] arr = {left, right};//자식노드들을 배열로 표현
            tree.put(parent, arr);
        }

        preorder("A");//A부터 전위순회하는 메소드 호출
        inorder("A");//A부터 중위순회하는 메소드 호출
        postorder("A");//A부터 후위순회하는 메소드 호출
        System.out.println(pre);
        System.out.println(ino);
        System.out.println(post);
    }

    //전위순회 메소드
    //루트노드 -> 왼쪽자식 -> 오른쪽자식
    public static void preorder(String root){
        pre.append(root);
        String[] child = tree.get(root);

        if(!child[0].equals(".")){//왼쪽자식이 있어야 재귀함수 호출
            preorder(child[0]);
        }

        if(!child[1].equals(".")){//오른족 자시기 있어야 재귀함수 호출
            preorder(child[1]);
        }
    }

    //중위순회 메소드
    //왼쪽자식 -> 루트 -> 오른쪽자식
    public static void inorder(String root){
        String[] child = tree.get(root);

        if(!child[0].equals(".")){
            inorder(child[0]);
        }

        ino.append(root);

        if(!child[1].equals(".")){
            inorder(child[1]);
        }
    }

    //후위순회 메소드
    //왼쪽자식 -> 오른쪽 자식 -> 루트
    public static void postorder(String root){
        String[] child = tree.get(root);

        if(!child[0].equals(".")){
            postorder(child[0]);
        }

        if(!child[1].equals(".")){
            postorder(child[1]);
        }

        post.append(root);
    }

}
