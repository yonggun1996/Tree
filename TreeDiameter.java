package Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
트리의 지름을 찾는 문제
루트노드(1번 노드)에서 가장 먼 곳을 찾는다
찾은 지점에서 먼 곳을 찾는다
두 지점간의 길이를 구한다
 */

public class TreeDiameter {

    static HashMap<Integer, ArrayList<TreeData>> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        if(n == 1){
            System.out.println(0);
        }else{
            for(int i = 1; i <= n; i++){
                ArrayList<TreeData> list = new ArrayList<>();
                map.put(i,list);
            }

            for(int i = 0; i < n - 1; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                int nd1 = Integer.parseInt(st.nextToken());
                int nd2 = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                ArrayList<TreeData> list1 = map.get(nd1);
                list1.add(new TreeData(nd2, weight));
                map.put(nd1, list1);

                ArrayList<TreeData> list2 = map.get(nd2);
                list2.add(new TreeData(nd1, weight));
                map.put(nd2, list2);
            }

            TreeData td1 = dfs(1);
            TreeData td2 = dfs(td1.next);

            System.out.println(td2.weight);
        }

    }

    public static TreeData dfs(int start){
        TreeData root_long = new TreeData(0, 0);
        Stack<TreeData> stack = new Stack<>();
        boolean[] visit = new boolean[map.size() + 1];
        visit[start] = true;
        ArrayList<TreeData> list = map.get(start);
        for(int i = 0; i < list.size(); i++){
            stack.push(list.get(i));
            visit[list.get(i).next] = true;
        }

        while(!stack.empty()){
            TreeData td = stack.pop();
            int now = td.next;
            int weight = td.weight;
            if(root_long.weight < weight){
                root_long = td;
            }

            ArrayList<TreeData> tree_list = map.get(now);

            for(int i = 0; i < tree_list.size(); i++){
                int next = tree_list.get(i).next;
                int next_weight = tree_list.get(i).weight;
                if(!visit[next]){
                    stack.push(new TreeData(next, next_weight + weight));
                    visit[next] = true;
                }
            }
        }

        return root_long;
    }
}

class TreeData{
    int next;
    int weight;

    public TreeData(int next, int weight){
        this.next = next;
        this.weight = weight;
    }
}