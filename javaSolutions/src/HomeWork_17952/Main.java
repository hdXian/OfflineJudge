package HomeWork_17952;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        System.out.println("n = " + n);

        int score, t;
        List<Work> works = new ArrayList<>();
        Work currentWork = null;
        int totalScore = 0;

        int workTime = 0;

        // 시간동안 진행
        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().split(" "); // 1(or 0) A T
            boolean isWorkAdd = line[0].equals("1");

            // 하는 일도 없고 추가된 일도 없을 때 건너뜀
            if(currentWork == null && !isWorkAdd)
                continue;

            // 일이 추가되면 현재 일을 변경
            if (isWorkAdd) {
                // 하던 일은 큐에 넣어둠 (가장 앞으로, 참조 전달)
//                if (currentWork != null)
                works.add(0, currentWork);

                // 현재 일을 새로 들어온 일로 변경
                score = Integer.parseInt(line[1]);
                t = Integer.parseInt(line[2]);
                System.out.printf("Work added: score=%d, time=%d\n", score, t);
                currentWork = new Work(score, t-1); // 받자마자 일을 함
            }
            else { // 일이 추가된게 아니면 하던 일을 수행 (남은시간 감소)
                currentWork.remainTime--;
            }

            // 시간이 다 되면 (일을 마치면) 점수를 더하고 다음 일을 꺼냄.
            if(currentWork.remainTime == 0) {
                System.out.println("complete work: " + currentWork);

                totalScore += currentWork.score;

                if (works.isEmpty()) {
                    currentWork = null;
                }
                else {
                    currentWork = works.get(0);
                    works.remove(0);
                }

                System.out.println("poll next work: " + currentWork);
            }

            System.out.println("1 minute passed");
            System.out.println("current work: " + currentWork);
        }

        System.out.println("works = " + works);
        System.out.println("totalScore = " + totalScore);
    }

    static class Work {

        public int score;
        public int remainTime;

        public Work(int score, int remainTime) {
            this.score = score;
            this.remainTime = remainTime;
        }

        @Override
        public String toString() {
            return String.format("Work score=[%s], remainTime=[%s]", score, remainTime);
        }
    }

}
