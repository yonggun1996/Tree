package Tree;
import java.io.*;
import java.util.*;

public class GoldTree {

    static int count = 0;
    static HashMap<Integer, ArrayList<Integer>> treemap = new HashMap<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        for(int i = 0; i < n; i++){
            treemap.put(i, new ArrayList<>());
        }

        int max_P = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            int parent = Integer.parseInt(st.nextToken());
            if(parent >= 0){
                ArrayList<Integer> list = treemap.get(parent);
                list.add(i);
                treemap.put(parent, list);
            }else{
                max_P = i;
            }
        }

        int delete = Integer.parseInt(br.readLine());
        ArrayList<Integer> list = treemap.get(max_P);
        int child_count = 0;

        if(delete == max_P){//최상위 부모노드를 삭제하면 트리가 없음
            System.out.println(0);
        }else{
            //최상위 노드에서 하위노드를 확인하는 코드
            for(int i = 0; i < list.size(); i++){
                if(search_child(list.get(i), delete)){
                    child_count++;//살펴볼 수 있으면 자식의 수를 늘린다
                }
            }

            if(child_count == 0){//해당 노드가 자식이 없는 노드면 count 증가
                count++;
            }

            System.out.println(count);
        }
    }

    //하위 노드들을 살피는 메소드
    public static boolean search_child(int parent, int delete){
        if(parent != delete){
            ArrayList<Integer> list = treemap.get(parent);//해당 위치 노드의 자식들의 리스트
            //소스 내용은 위 main문과 동일
            int child_count = 0;
            for(int i = 0; i < list.size(); i++){
                if(search_child(list.get(i), delete)){
                    child_count++;
                }
            }

            if(child_count == 0){
                count++;
            }
            return true;//자식노드를 살펴봤기 때문에 true 반환
        }else{
            return false;//더이상 자식노드를 볼 수 없기 때문에 false 반환
        }
    }
}

