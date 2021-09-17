package Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
2번 노드부터 부모 노드를 찾는 문제
트리를 HashMap으로 생성
1번 노드에서 부터 BFS로 트리를 순회
배열의 인덱스는 자식 노드 번호, 안에 내용은 부모노드 번호이다.
배열을 순서대로 출력
 */

public class SearchParent {

    static int[] level;//부모노드를 담는 배열
    static HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();//실질적인 트리 역할을 하는 map

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        level = new int[n + 1];

        for(int i = 1; i <= n; i++){//리스트 생성
            ArrayList<Integer> list = new ArrayList<>();
            map.put(i, list);
        }

        //트리 생성하는 for문
        for(int i = 0; i < n - 1; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int nd1 = Integer.parseInt(st.nextToken());
            int nd2 = Integer.parseInt(st.nextToken());

            //각각 노드를 연결
            ArrayList<Integer> list1 = map.get(nd1);
            list1.add(nd2);
            map.put(nd1, list1);

            ArrayList<Integer> list2 = map.get(nd2);
            list2.add(nd1);
            map.put(nd2, list2);
        }

        //BFS로 트리를 탐색
        Queue<ParentData> queue = new LinkedList<>();
        ArrayList<Integer> list = map.get(1);
        boolean[] visit = new boolean[n + 1];
        visit[1] = true;

        //루트노드인 1번 노드에 연결된 노드를 담는다
        for(int i = 0; i < list.size(); i++){
            queue.offer(new ParentData(1, list.get(i)));
        }

        while(!queue.isEmpty()){
            ParentData pd = queue.poll();
            int parent = pd.parent;
            int now = pd.now;

            level[now] = parent;//부모노드 초기화
            visit[now] = true;//방문처리
            ArrayList<Integer> child_list = map.get(now);

            //현재 노드에서 연결된 노드를 확인
            for(int i = 0; i < child_list.size(); i++){
                if(!visit[child_list.get(i)]){
                    queue.offer(new ParentData(now, child_list.get(i)));
                }
            }
        }

        for(int i = 2; i <= n; i++){
            System.out.println(level[i]);
        }
    }
}

class ParentData{
    int parent;//해당 노드의 부모노드
    int now;//해당 노드

    public ParentData(int parent, int now){
        this.parent = parent;
        this.now = now;
    }
}
