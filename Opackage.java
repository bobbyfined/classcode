package opackage;

public class Opackage {
	public static void knapsack(int[] v, int[] w, int c, int[][] m)
	{
		int n = v.length - 1;
		int jMax = Math.min(w[n], c);
		//初始化数组 m
		for(int j = 0; j <= jMax; j++)
		{
			m[n][j] = 0;
		}
		//只有物品 n 时，如果物品 n 的质量小于背包容量，则向背包中放入物品 n
		for(int j = w[n]; j <= c; j++)	
		{
			m[n][j] = v[n];
		}
		//如果背包容量允许，则陆续向背包中添加物品 n-1, n-3 ... 1
		for(int i = n - 1; i >= 0; i--)
		{
			jMax = Math.min(w[i], c);	
			//当背包容量为 j 时，记录物品 i+1（n）
			for(int j = 0; j <= jMax; j++)
			{
				m[i][j] = m[i + 1][j];
			}
			//如果放入物品 i 则最大价值为 m[i + 1][j - w[i]] + v[i] , 如果不放入物品 i 则最大值为 m[i + 1][j]
			for(int j = w[i]; j <= c; j++)
			{
				m[i][j] = Math.max(m[i + 1][j], m[i + 1][j - w[i]] + v[i]);
			}				
			m[0][c] = m[1][c];
			if(c >= w[1])
			{
				m[0][c] = Math.max(m[0][c], m[1][c - w[0]] + v[0]);
			}
		}
	}
	
	//构造最优解，判断是否向背包放入物品 i
	public static void traceback(int[][] m, int[] w, int c, int[] x)
	{
		int n = w.length - 1;
		for(int i = 0; i < n; i++)
		{
			//如果没有向背包中放入物品，则 x[i] 置为 0
			if(m[i][c] == m[i + 1][c])
			{
				x[i] = 0;
			}
			else //向背包中放入了物品，x[i] 置为 1
			{
				x[i] = 1;
				c -= w[i];	//背包的剩余容量
			}
		}
		x[n] = (m[n][c] > 0) ? 1 : 0;	//判断最后一件物品有没有放入背包中
	}
	
	public static void main(String[] args)
	{
		int[] v = {20, 6, 8, 15, 18};
		int[] w = {5, 4, 8, 6, 9};		 
		int c = 18;
		int[][] m = new int[w.length][c + 1];
		int[] x = new int[w.length];
		
		knapsack(v, w, c, m);
		traceback(m, w, c, x);
		
		for(int i : x)
			System.out.print(i + "	");
		System.out.println();
		
		for(int[] i : m)
		{
			for(int j : i)
				System.out.print(j + "	");
			System.out.println();
		}
	}
}
