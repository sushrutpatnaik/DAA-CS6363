
// Starter code for CS 6363.004 Project (Spring 2018).  Modify as needed.

// Change name of package from "NetId" to your net id
package sxp175331;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


// Change name of file and class from "NetId" to your net id
public class sxp175331Project6363 {
	static int VERBOSE = 0;
	static int[][] jwlr;

	static class Jewel {
		public int weight, profit, min, max, fine, cap;

		Jewel(int w, int p, int n, int x, int f, int c) {
			weight = w;
			profit = p;
			min = n;
			max = x;
			fine = f;
			cap = c;
		}

		public String toString() {
			return weight + " " + profit + " " + min + " " + max + " " + fine + " " + cap;
		}
	}

	static class Pair {
		public int p, n;

		Pair(int p, int n) {
			this.p = p;
			this.n = n;
		}

		public String toString() {
			return p + " " + n;
		}
	}

	public static Pair process(int G, Jewel[] items, int n) {
		jwlr = new int[n+1][G+1];
		int profit_max = Integer.MIN_VALUE;
		int[][] arr_count = new int[n+1][G+1];
		int ctr=0;
		
		for(int i = 1;i<=n;i++)
		{
			for(int g=0;g<=G;g++)
			{
				arr_count[0][g]=1;
				profit_max=Integer.MIN_VALUE;
				
				for(int q=0; q<=items[i].max; q++)
				{	
				 
					if(g-(q*items[i].weight)<0)
					{
						break;
					}
					int profit;
					if (q < items[i].min) {
						
						profit = (q * items[i].profit + jwlr[i - 1][g - q * items[i].weight]
								- Math.min(items[i].cap, items[i].fine * (items[i].min - q)));
						
					} else {
						profit = (q * items[i].profit + jwlr[i - 1][g - q * items[i].weight]);
						
					}
					
					if (profit_max < profit) 
					{
						profit_max = profit;
						ctr=arr_count[i-1][g-q*items[i].weight];
						
					} 
					else if (profit_max == profit) {
						ctr = ctr + arr_count[i-1][g-q*items[i].weight] ;
					}
				}
				arr_count[i][g]=ctr;
				jwlr[i][g]=profit_max;
							
			}
			
		}
			
		return new Pair(profit_max, ctr);
	}
	
	public static void enumerate(int g, Jewel[] items, int i, int s[], int mp[][])
	{
		
		
		if(i==0)
		{
			for(int j = 1; j < s.length; j++) {
				System.out.print(s[j]+" ");
			}
			System.out.println();
		}
		else
		{
			for(int q=0;Math.min(items[i].max-q, g-q*items[i].weight)>=0;q++)
			{	
				if(q<items[i].min)
				{
					if(mp[i][g]== mp[i-1][g-q*items[i].weight] + q*items[i].profit - Math.min(items[i].cap, items[i].fine * (items[i].min - q)))
					                      {
											s[i]=q;											
											enumerate(g-q*items[i].weight, items, i-1,s, mp);
					                      }
				}
				else
				{
					if(mp[i][g]== mp[i-1][g-q*items[i].weight] + q*items[i].profit)
					{
						s[i]=q;
						enumerate(g-q*items[i].weight, items, i-1,s, mp);
					}
				}
			}
			
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in;
		if (args.length == 0 || args[0].equals("-")) {
			in = new Scanner(System.in);
		} else {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		}
		if (args.length > 1) {
			VERBOSE = Integer.parseInt(args[1]);
		}
		int G = in.nextInt();
		int n = in.nextInt();

		Jewel[] items = new Jewel[n + 1];
		for (int i = 0; i < n; i++) {
			int index = in.nextInt();
			int weight = in.nextInt();
			int profit = in.nextInt();
			int min = in.nextInt();
			int max = in.nextInt();
			int fine = in.nextInt();
			int cap = in.nextInt();
			items[index] = new Jewel(weight, profit, min, max, fine, cap);
			if (VERBOSE > 0) {
				System.out.println(index + " " + items[index]);
			}
		}
		Pair answer = process(G, items, n);
		//# Output
		System.out.println(answer);
		if (VERBOSE > 0) {
			//# List of optimal solutions
			int[] s=new int[n+1];
			enumerate(G, items, n, s , jwlr );
		}		
		
	}
	
}
