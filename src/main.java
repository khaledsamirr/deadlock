import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class main {
    public static void main(String args[]) {
        int numOfProcesses = 5;
        int numOfResources = 3;
        int need[][] = new int[numOfProcesses][numOfResources];
        int allocated[][] = {{1, 1, 0}, {2, 0, 0}, {2, 0, 1}, {2, 0, 2}, {0, 1, 2}};
        int max[][] = {{5, 5, 3}, {3, 2, 3}, {8, 0, 2}, {4, 2, 2}, {5, 3, 5}};
        int or_available[] = {2, 5, 4};
        int available[] = or_available;

        Boolean deadLockState = true;
        String processesFinishSequence = "";
        String processesDeadLockSequence = "";
        Boolean finish[] = new Boolean[numOfProcesses];
        Scanner scanner = new Scanner(System.in);
        String cmd = "";
        String[] cmds = new String[0];
        String[] num = new String[0];
        int m = 0;
        int[] request = new int[3];
        boolean flag;

        while (!cmd.equals("quit")) {

            available = or_available;

            for (int i = 0; i < numOfProcesses; i++) {
                finish[i] = false;
            }

            need = calcNeed(allocated, max, need);
            System.out.println("=====================================================");

            while (true) {
                int j;

                for (int i = 0; i < need.length; i++) {

                    if (processesDeadLockSequence.length() + numOfFinished(finish) >= numOfProcesses) {
                        deadLockState = true;
                        break;
                    }

                    if (!finish[i]) {

                        processesDeadLockSequence += i;

                        System.out.println("Checking P" + i);
                        System.out.println("----------------------------------------------");
                        System.out.println("\t   Need" + "\t\tAvailable");
                        System.out.println("P" + i + " |  " + need[i][0] + " " + need[i][1] + " " + need[i][2] + "\t\t" + available[0] + " " + available[1] + " " + available[2]);


                        for (j = 0; j < need[0].length; j++) {
                            System.out.println("Checking Resource at column (" + (j + 1) + ") if it is sufficient to complete the work.");
                            if (need[i][j] > available[j]) {
                                System.out.println("Resource is insufficient");
                                break;
                            }
                        }

                        if (j >= need[0].length) {
                            System.out.println("Resources are sufficient to Finish the work of P" + i);
                            for (int q = 0; q < need[i].length; q++) {
                                available[q] += allocated[i][q];
                            }
                            processesFinishSequence += i;
                            finish[i] = true;
                            processesDeadLockSequence = "";
                            System.out.println("P" + i + " finished it's work.");
                        } else {
                            System.out.println("P" + i + " Can't finish as it exceeds the available resources at column " + (j + 1));
                        }

                        System.out.println("----------------------------------------------");
                    }


                }

                if (numOfFinished(finish) >= finish.length) {
                    deadLockState = false;
                    break;
                }

                if (processesDeadLockSequence.length() + numOfFinished(finish) >= numOfProcesses) {
                    deadLockState = true;
                    break;
                }

            }


            if (deadLockState) {
                System.out.println("DeadLock Sequence:");
                for (int i = 0; i < processesDeadLockSequence.length(); i++) {
                    System.out.print(" P" + processesDeadLockSequence.charAt(i));
                }
            } else if (!deadLockState) {
                System.out.println("Safe Sequence:");
                for (int i = 0; i < numOfProcesses; i++) {
                    System.out.print(" P" + processesFinishSequence.charAt(i));
                }
            }

            System.out.println();


            System.out.println("Enter command:");
            cmd = scanner.nextLine();
            cmds = cmd.split(" ");
            num = cmds[1].split("");
            m = Integer.parseInt(num[1]);
            for (int i = 0; i < cmds.length - 2; i++) {
                request[i] = Integer.parseInt(cmds[i + 2]);
            }
            if (cmds[0].equals("RQ")) {
                flag = checkRequest(request, or_available, need, m, cmds.length - 2);
                if (flag) {
                    for (int i = 0; i < cmds.length - 2; i++) {
                        allocated[m][i] += request[i];
                        need[m][i] -= request[i];
                        or_available[i] -= request[i];
                    }
                    System.out.println("Allocated: ");
                    print2D(allocated);
                    System.out.println("Need: ");
                    print2D(need);
                    System.out.println("Available: " + Arrays.toString(or_available));

                } else {
                    System.out.println("can't request!");
                }

            } else if (cmds[0].equals("RL")) {
                flag = checkRelease(request, allocated, need, m, cmds.length - 2);
                if (flag) {
                    for (int i = 0; i < cmds.length - 2; i++) {
                        allocated[m][i] -= request[i];
                        need[m][i] += request[i];
                        or_available[i] += request[i];
                    }
                    System.out.println("Allocated: ");
                    print2D(allocated);
                    System.out.println("Need: ");
                    print2D(need);
                    System.out.println("Available: " + Arrays.toString(or_available));

                } else {
                    System.out.println("can't release!");
                }

            } else if (cmds[0].equals("quit")) {
                break;
            } else {
                System.out.println("Wrong command, please enter a valid one!");
            }
        }


    }

    public static int numOfFinished(Boolean finished[]) {
        int counter = 0;
        for (int i = 0; i < finished.length; i++) {
            if (finished[i]) {
                counter++;
            }
        }
        return counter;
    }

    public static boolean checkRequest(int[] input, int[] condition, int[][] need, int m, int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            if (input[i] <= condition[i]) {
                counter++;
            }
        }
        if (counter == n) {
            counter = 0;
            for (int i = 0; i < n; i++) {
                if (input[i] <= need[m][i])
                    counter++;
            }
            if (counter == n)
                return true;
            else
                return false;
        } else
            return false;

    }

    ;

    public static boolean checkRelease(int[] input, int[][] allo, int[][] need, int m, int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            if (input[i] <= allo[m][i]) {
                counter++;
            }
        }
        if (counter == n) {
            return true;
        } else
            return false;

    }

    ;

    public static int[][] calcNeed(int allocated[][], int max[][], int need[][]) {
        for (int i = 0; i < allocated.length; i++) {
            for (int j = 0; j < allocated[i].length; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }

        return need;
    }

    public static void print2D(int mat[][]) {
        for (int i = 0; i < mat.length; i++) {

            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

}
