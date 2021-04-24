public class main {
    int numOfProcesses = 5;
    int numOfResources = 3;
    int need[][] = new int[numOfProcesses][numOfResources];
    int [][]allocated= {{1,1,0},{2,0,0},{2,0,1},{2,0,2},{0,1,2}};
    int [][]max={{5,5,3},{3,2,3},{8,0,2},{4,2,2},{5,3,5}};
    int []available={2,5,4};
    int safe[] = new int[numOfProcesses];
    boolean isSafe() {
        int count=0;
        boolean b[] = new boolean[numOfProcesses];
        for (int i = 0;i < numOfProcesses; i++)
        {
            b[i] = false;
        }

        int work[] = new int[numOfResources];
        for (int i = 0;i < numOfResources; i++)
        {
            work[i] = available[i];
        }

        while (count<numOfProcesses)
        {
            boolean flag = false;
            for (int i = 0;i < numOfProcesses; i++)
            {
                if (b[i] == false)
                {
                    int k;
                    for (k = 0;k < numOfResources; k++)
                    {
                        if (need[i][k] > work[k])
                            break;
                    }
                    if (k == numOfResources)
                    {
                        safe[count++]=i;
                        b[i]=true;
                        flag=true;

                        for (k = 0;k < numOfResources; k++)
                        {
                            work[k] = work[k]+allocated[i][k];
                        }
                    }
                }
            }
            if (flag == false)
            {
                break;
            }
        }
        if (count < numOfProcesses)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    void calcNeed() {
        for (int i = 0;i < numOfProcesses; i++)
        {
            for (int j = 0;j < numOfResources; j++)
            {
                need[i][j] = max[i][j]-allocated[i][j];
            }
        }
    }
}
