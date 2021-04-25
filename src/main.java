import java.util.ArrayList;

public class main {
    public static void main(String args[]) {
        int numOfProcesses = 5;
        int numOfResources = 3;
        int need[][] = new int[numOfProcesses][numOfResources];
        int allocated[][] = {{0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 0, 2}};
        int max[][] = {{7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {2, 2, 2}, {4, 3, 3}};
        int available[] = {3, 3, 2};
        Boolean deadLockState = true;
        String processesFinishSequence = "";
        String processesDeadLockSequence = "";
        Boolean finish[] = new Boolean[numOfProcesses];
        int allFinishCounter = 0;

        for (int i = 0; i < numOfProcesses; i++) {
            finish[i] = false;
        }

        need = calcNeed(allocated, max, need);


        while (true) {
            int j;

            for (int i = 0; i < need.length; i++) {

                if (!finish[i]) {

                    processesDeadLockSequence += i;

                    for (j = 0; j < need[0].length; j++) {
                        if (need[i][j] > available[j]) {
                           // System.out.println(j);
                            break;
                        }
                    }
                    System.out.println("P"+i);
                    System.out.println(available[0]+""+available[1]+""+available[2]);
                    if (j >= need[0].length) {
                        for (int q = 0; q < need[i].length; q++) {
                            available[q] += allocated[i][q];
                            allocated[i][q] = 0;
                            need[i][q] = 0;
                        }
                        processesFinishSequence += i;
                        finish[i] = true;
                        processesDeadLockSequence = "";
                    }
                }

            }

            for (int i = 0; i < finish.length; i++) {
                if (finish[i]) {
                    allFinishCounter++;
                }
            }

            if (allFinishCounter >= finish.length) {
                deadLockState = false;
                break;
            }

            if (processesDeadLockSequence.length() + allFinishCounter >= need.length) {
                deadLockState = true;
                break;
            }

        }


        if (deadLockState) {
            System.out.println("DeadLock Sequence:");
            for (int i = 0; i < numOfProcesses; i++) {
                System.out.print(" P" + processesDeadLockSequence.charAt(i));
            }
        } else if (!deadLockState) {
            System.out.println("Finished Sequence:");
            for (int i = 0; i < numOfProcesses; i++) {
                System.out.print(" P" + processesFinishSequence.charAt(i));
            }
        }

    }


    public static int[][] calcNeed(int allocated[][], int max[][], int need[][]) {
        for (int i = 0; i < allocated.length; i++) {
            for (int j = 0; j < allocated[i].length; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }

        return need;
    }

}
