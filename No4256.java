package Avatar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/*
전위순회, 중위순회 결과값을 받아서 후위순회로 출력을 해야 하는 문제
스택을 이용해 루트, 방향, 리스트를 자르는 구간을 담아
트리를 만든 후 후위순회로 트리를 탐색하면서 StringBuilder에 노드를 추가한다
 */

public class No4256 {
    static ArrayList<Integer> preorderList;//입력받은 전위순회 순서
    static ArrayList<Integer> inorderList;//입력받은 중위순회 순서
    static StringBuilder answer;//후위순회 결과
    static int[][] treeData;//각 노드에 좌측, 우측에 있는 노드(0은 자식노드가 없다고 보면 된다.)
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        for(int i = 0; i < testcase; i++){
            preorderList = new ArrayList<>();
            inorderList = new ArrayList<>();
            int n = Integer.parseInt(br.readLine());

            treeData = new int[n + 1][2];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++){
                preorderList.add(Integer.parseInt(st.nextToken()));
            }

            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++){
                inorderList.add(Integer.parseInt(st.nextToken()));
            }

            int first_root = preorderList.get(0);//최상단 루트 노드는 전위순회 리스트 중 제일 앞에 있는 노드
            int rt_idx = inorderList.indexOf(first_root);//최상단 루트노드를 토대로 나누기 위한 기준
            Stack<TmpTreeData> stack = new Stack<>();

            //루트노드를 기준으로 반으로 나눈다
            if(rt_idx + 1 <= n - 1){//나눴을 때 오른쪽 데이터가 없으면 스택에 넣지 않는다
                stack.push(new TmpTreeData(first_root, 'R', rt_idx + 1, n - 1));
            }

            if(rt_idx - 1 >= 0){//나눴을 때 왼쪽 데이터가 없으면 스택에 넣지 않는다
                stack.push(new TmpTreeData(first_root, 'L', 0, rt_idx - 1));
            }

            int list_idx = 1;//전위순회 리스트의 인덱스
            while(!stack.isEmpty()){
                TmpTreeData ttd = stack.pop();
                int root = ttd.root;//루트노드
                char c = ttd.dec;//방향
                int left = ttd.start;//리스트의 시작 부분
                int right = ttd.end;//리스트의 끝 부분

                int value = preorderList.get(list_idx);//들어갈 노드
                if(c == 'L'){//왼쪽인 경우 왼쪽에 데이터 삽입
                    treeData[root][0] = value;
                }else{//오른쪽인 경우 오른쪽에 데이터 삽입
                    treeData[root][1] = value;
                }

                //다음 루트를 기준으로 리스트를 나눈다
                int next_Root = value;
                int next_idx = inorderList.indexOf(next_Root);
                list_idx++;//전위순회 리스트의 다음 데이터를 살피기 위해 인덱스 변수 증가

                //마찬가지로 스택에 넣기 위한 if문으로 스택에 데이터를 넣는다
                if(next_idx + 1 <= right){
                    stack.push(new TmpTreeData(next_Root, 'R', next_idx + 1, right));
                }

                if(next_idx - 1 >= left){
                    stack.push(new TmpTreeData(next_Root, 'L', left, next_idx - 1));
                }

            }

            //후위순회를 한 데이터를 토대로 정답을 출력한다
            answer = new StringBuilder();
            postorder(first_root);
            System.out.println(answer);
        }
    }

    //후위순회를 하는 메서드
    static void postorder(int root){
        int[] value = treeData[root];
        if(value[0] > 0){
            postorder(value[0]);
        }

        if(value[1] > 0){
            postorder(value[1]);
        }

        answer.append(root).append(" ");
    }
}

class TmpTreeData{
    int root;//자식의 대상이 될 루트
    char dec;//어느쪽 자식인지 확인할 수 있는 문자
    int start;//리스트의 시작점
    int end;//리스트의 끝점

    public TmpTreeData(int root, char dec, int start, int end){
        this.root = root;
        this.dec = dec;
        this.start = start;
        this.end = end;
    }
}