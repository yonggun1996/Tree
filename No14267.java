package Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
트리와 다이나믹 프로그래밍을 동시에 사용하는 문제
일반적으로 트리에서 탐색을 하면 최대 입력이 100000이기 때문에 n^2으로 오래 걸릴 수 있다.
dp를 이용해 각 직원이 칭찬 받은 지수를 자식에게 그대로 전달
1번 노드부터 DFS를 하면서 지수를 더해 각 칭찬지수를 구할 수 있다.
 */

public class No14267 {
    static HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();//트리구조를 저장한 맵
    static int[] dp;//각 사원이 칭찬받은 수치
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        for(int i = 1; i <= n; i++){
            map.put(i, new ArrayList<>());
        }
        dp = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= n; i++){
            int nd = Integer.parseInt(st.nextToken());
            if(nd == -1) continue;//사장은 맵에 넣지 않는다

            ArrayList<Integer> list = map.get(nd);//직속상사의 부하직원 리스트를 가져온다
            list.add(i);//부하직원 추가
            map.put(nd, list);
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());//칭찬받은 직원
            int w = Integer.parseInt(st.nextToken());//칭찬지수

            dp[start] += w;//먼저 배열에 칭찬지수를 추가한다
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(1);

        //DFS로 상사가 받은 칭찬 지수를 부하직원에게 추가
        while(!stack.isEmpty()){
            int nd = stack.pop();//현재 직원
            ArrayList<Integer> list = map.get(nd);

            for(int i = 0; i < list.size(); i++){
                int next = list.get(i);//부하직원
                dp[next] += dp[nd];//부하직원에게 상사가 받은 칭찬 지수를 누적시킨다
                stack.push(next);
            }
        }

        StringBuilder answer = new StringBuilder();
        for(int i = 1; i <= n; i++){
            answer.append(dp[i]).append(" ");
        }
        System.out.println(answer);
    }
}
