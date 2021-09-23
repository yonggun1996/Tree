# Tree

### 트리 : 계층적인 구조를 표현하기 위한 자료구조.

다음의 그림이 트리구조를 표현한 그림 입니다.

![트리 자료구조](https://user-images.githubusercontent.com/68115246/134507565-8650ced8-9c79-4c83-958b-6947bb915223.png)


### 용어
1. 루트노드 : 부모가 없는 노드로 해당 이미지에서는 1이 루트노드 입니다.
2. 단말노드 : 자식이 없는 노드로 해당 이미지에서는 4, 5, 6 노드가 단말노드 입니다.
3. 크기 : 트리에 포함된 노드들의 개수로 해당 이미지에선 사이즈가 6 입니다.
4. 깊이 : 루트노드로 부터 최장거리를 말하며 해당 이미지에서의 트리는 깊이가 3 입니다.
5. 높이 : 깊이 중 최대 깊이로 깊읻도 3 입니다.
6. 차수 : 각 노드가 가지고 있는 자식 수를 말합니다. 1번 노드는 차수가 2, 3번노드는 차수가 1, 6번 노드는 차수가 0 입니다.

### 순회 : 트리 자료구조를 특정 방법으로 한번 씩 방문하는 방법입니다.
- 전위순회 : 루트 -> 왼쪽 -> 오른쪽 순으로 순회하는 방법 입니다.
- 중위순회 : 왼쪽 -> 루트 -> 오른쪽 순으로 순회하는 방법 입니다.
- 후위순회 : 왼쪽 -> 오른쪽 -> 루트 순으로 순회하는 방법 입니다.

위의 이미지대로 각각의 순회방법은 다음과 같습니다.
- 전위순회 결과 : 1 -> 2 -> 4 -> 5 -> 3 -> 6
- 중위순회 결과 : 4 -> 2 -> 5 -> 1 -> 3 -> 6 (6은 3의 오른쪽 자식이라 가정했습니다.)
- 후위순회 결과 : 4 -> 5 -> 2 -> 6 -> 3 -> 1

### 각 순회를 구현한 소스코드 (백준 알고리즘 1991번 문제)
```java
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
```
