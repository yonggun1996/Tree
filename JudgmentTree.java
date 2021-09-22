package Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
도움이 된 블로그 : https://imnotabear.tistory.com/452
유니온 파인드 알고리즘 : 하나로 연결된 그래프의 개수를 구할 수 있는 알고리즘
재귀함수를 이용해 제일 작은 노드를 통해 서로 연결되어있는 값들을 알 수 있다.
이렇게 유니온 파인드 알고리즘을 두번 이용, Set를 이용해 가장 작은 노드를 담아 연결된 트리의 개수를 확인
배열에 0이 들어간 경우는 싸이클이 존재하는 경우
 */

public class JudgmentTree {

    static int[] parent;//경로를 추적했을 때 제일 작은 수를 담는 배열

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = 1;
        while(true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            if (n == 0 && m == 0) {//코드가 끝나는 조건
                break;
            }

            parent = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int node1 = Integer.parseInt(st.nextToken());
                int node2 = Integer.parseInt(st.nextToken());
                //유니온 파인드 알고리즘을 이용해 주어진 입력에 따른 최소 노드를 찾는다
                int parent1 = set_parent(node1);
                int parent2 = set_parent(node2);
                int min = Math.min(parent1, parent2);
                int max = Math.max(parent1, parent2);
                //노드가 큰 값은 인덱스가 되고 작은 값을 그 배열에 넣는다

                parent[max] = min;
                if(parent1 == parent2){
                    parent[min] = 0;//부모가 같다는 의미로 싸이클을 형성한다는 의미
                }
            }

            //같은 함수를 호출해 최소 노드를 찾는다
            HashSet<Integer> set = new HashSet<>();//최소 노드들을 담는 Set
            for(int i = 1; i <= n; i++){
                int result = set_parent(i);
                if(result > 0){
                    set.add(result);
                }
            }

            if(set.size() == 0){
                System.out.printf("Case %d: No trees.\n", testcase);
            }else if(set.size() == 1){
                System.out.printf("Case %d: There is one tree.\n", testcase);
            }else{
                System.out.printf("Case %d: A forest of %d trees.\n", testcase, set.size());
            }
            testcase++;

        }

    }

   public static int set_parent(int node){
        if(parent[node] == node){//찾으려는 노드의 부모가 같다면 node를 반환
            return node;
        }

        return set_parent(parent[node]);//재귀함수를 반복해 위의 if문으로 return 받은 값을 반환
   }

}
