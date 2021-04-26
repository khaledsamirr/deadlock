import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

/*
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
                    System.out.println("P" + i);
                    System.out.println(available[0] + "" + available[1] + "" + available[2]);
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
      */

    /*    if (deadLockState) {
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

     */

        Scanner scanner = new Scanner(System.in);
        String cmd="";
        String[] cmds = new String[0];
        String [] num = new String[0];
        int m=0;
        int [] request = new int[3];
        boolean flag;
        while (!cmd.equals("quit")){
            System.out.println("Enter command:");
            cmd=scanner.nextLine();
            cmds = cmd.split(" ");
            num=cmds[1].split("");
            m=Integer.parseInt(num[1]);
            for(int i=0;i<cmds.length-2;i++){
                request[i]=Integer.parseInt(cmds[i+2]);
            }
            if(cmds[0].equals("RQ")){
                flag=checkRequest(request,available,cmds.length-2);
                if(flag){
                    for(int i=0;i<cmds.length-2;i++){
                        allocated[m][i]+=request[i];
                        need[m][i]=max[m][i]-allocated[m][i];
                        available[i]-=request[i];
                    }
                    System.out.println("Allocated: ");
                    print2D(allocated);
                    System.out.println("Need: ");
                    print2D(need);
                    System.out.println("Available: "+ Arrays.toString(available));

                }
                else{
                    System.out.println("can't request!");
                }

            }
            else if(cmds[0].equals("RL")){
                flag=checkRelease(request,allocated,m,cmds.length-2);
                if(flag){
                    for(int i=0;i<cmds.length-2;i++){
                        allocated[m][i]-=request[i];
                        need[m][i]=max[m][i]-allocated[m][i];
                        available[i]+=request[i];
                    }
                    System.out.println("Allocated: ");
                    print2D(allocated);
                    System.out.println("Need: ");
                    print2D(need);
                    System.out.println("Available: "+ Arrays.toString(available));

                }else{
                    System.out.println("can't release!");
                }

            }
            else if(cmds[0].equals("quit")){
                break;
            }
            else{
                System.out.println("Wrong command, please enter a valid one!");
            }
        }



    }


    
    public static boolean checkRequest(int[] input,int[]condition,int n){
        int counter=0;
        for(int i=0;i<n;i++){
            if(input[i]<=condition[i]){
                counter++;
            }
        }
        if(counter==n)
            return true;
        else
            return false;

    };

    public static boolean checkRelease(int[] input,int[][] allo,int m,int n){
        int counter=0;
        for(int i=0;i<n;i++){
            if(input[i]<=allo[m][i]){
                counter++;
            }
        }
        if(counter==n)
            return true;
        else
            return false;

    };
    public static int[][] calcNeed(int allocated[][], int max[][], int need[][]) {
        for (int i = 0; i < allocated.length; i++) {
            for (int j = 0; j < allocated[i].length; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }

        return need;
    }
    public static void print2D(int mat[][])
    {
        for (int i = 0; i < mat.length; i++) {

            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

}
