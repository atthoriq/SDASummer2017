import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.invoke.WrongMethodTypeException;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Stack;

public class SDA1606918484T2 
{
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
		
		HashMap<String, Hero> nameOfHeroes = new HashMap<>();
		HashMap<Path, ArrayList<Hero>> summonLocs = new HashMap<>();
		HashMap<Path, Dungeon> dungeonLocs = new HashMap<>();
		char[][] map = new char[111][111];
		
		
		String[] instruction = input.readLine().split(" ");
		
		int sumHero = Integer.parseInt(instruction[0]);
		int sumSummon = Integer.parseInt(instruction[1]);
		int sumDungeon = Integer.parseInt(instruction[2]);
		int manaGudako = Integer.parseInt(instruction[3]);
		int row = Integer.parseInt(instruction[4]);
		int col = Integer.parseInt(instruction[5]);
		
		while(sumHero != 0)
		{
			instruction = input.readLine().split(";");
			
			String name = instruction[0];
			int mana = Integer.parseInt(instruction[1]);
			int power = Integer.parseInt(instruction[2]);
			String weapon = instruction[3];
			
			nameOfHeroes.put(name, new Hero(name, mana, power, weapon));
			
			sumHero--;
		}
		
		while(sumSummon != 0)
		{
			instruction = input.readLine().split(";");
			
			int r = Integer.parseInt(instruction[0]);
			int c = Integer.parseInt(instruction[1]);
			String heroesOnSummon = instruction[2];
			
			Path tempPath = new Path(r,c);
			ArrayList<Hero> listOfHero = new ArrayList<>();
			
			StringTokenizer tokenizer = new StringTokenizer(heroesOnSummon, ",");
			
			while(tokenizer.hasMoreTokens())
			{
				listOfHero.add(nameOfHeroes.get(tokenizer.nextToken()));
			}
			
			
			Collections.sort(listOfHero);
			summonLocs.put(tempPath, listOfHero);
			
			sumSummon--;
		}
		
		while(sumDungeon != 0)
		{
			instruction = input.readLine().split(";");
			
			int r = Integer.parseInt(instruction[0]);
			int c = Integer.parseInt(instruction[1]);
			
			int power = Integer.parseInt(instruction[2]);
			int level = Integer.parseInt(instruction[3]);
			String weapon = instruction[4];
			int maxHero = Integer.parseInt(instruction[5]);
			
			Path tempPath = new Path(r,c);
			
			dungeonLocs.put(tempPath, new Dungeon(power,level,maxHero,weapon));
			
			sumDungeon--;
		}
		
		// Gudako
		ArrayList<Hero> gudakoHero = new ArrayList<>();
		int levelGudako = 1;
		int rankHero = 0;
		Stack<Path> gudakoPosition = new Stack<>();
		
		// Instantiate map
		for(int r=1; r <= row; r++)
		{
			for(int c=1; c <= col; c++)
			{
				map[r][c] = (char) input.read();
				
				if(map[r][c] == 'M')
				{
					gudakoPosition.push(new Path(r,c));
				}
			}
			input.readLine();
		}
		
		// Run
		while(!gudakoPosition.empty())
		{
			Path track = gudakoPosition.pop();
			int r = track.row;
			int c = track.col;
			
			char path = map[r][c];
			
			if(path == 'X')
				continue;
			
			if(path == 'S')
			{
				// SUMMON
				ArrayList<Hero> heroesOnPath = summonLocs.get(track);
				ArrayList<String> nameOfJoined = new ArrayList<>();
				
				int tempMana = manaGudako;
				
				for(int i = 0; i < heroesOnPath.size(); i++)
				{
					if(tempMana >= heroesOnPath.get(i).mana)
					{
						heroesOnPath.get(i).rank = rankHero;
						gudakoHero.add(heroesOnPath.get(i));
						nameOfJoined.add(heroesOnPath.get(i).name);
						
						tempMana -= heroesOnPath.get(i).mana;
					}
					else
						break;
				}
				
				if(!nameOfJoined.isEmpty())
				{
					output.write(r + "," + c + " Pahlawan yang ikut:");
					
					for(int i = 0; i < nameOfJoined.size(); i++)
					{
						if(i > 0)
						{
							output.write(",");
						}
						output.write(nameOfJoined.get(i));
					}
				}
				
				else
				{
					output.write(r + "," + c + " tidak ada pahlawan yang ikut");
				}
				
				output.newLine();
				rankHero++;
			}
			
			else if(path == 'D')
			{
				// BATTLE
				Dungeon foe = dungeonLocs.get(track);
				Collections.sort(gudakoHero, new sortHeroesBattle(foe.weapon));
				
				int maxPower = 0;
				ArrayList<String> nameOfFighter = new ArrayList<>();
				
				int limit = Math.min(gudakoHero.size(), foe.maxHero);
				
				for(int i = 0; i < limit; i++)
				{
					maxPower += gudakoHero.get(i).getTempPower(foe.weapon);
					nameOfFighter.add(gudakoHero.get(i).name);
				}
				
				if(maxPower >= foe.power)
				{
					output.write( r + "," + c + " BATTLE, kekuatan: " + maxPower + ", pahlawan: ");
					
					for(int i = 0; i < nameOfFighter.size(); i++)
					{
						if( i > 0 )
						{
							output.write(",");
						}
						output.write(nameOfFighter.get(i));
						gudakoHero.get(i).level += foe.level;
					}
					
					levelGudako += (nameOfFighter.size()*foe.level);
				}
				
				else
				{
					output.write(r + "," + c + " RUN, kekuatan maksimal sejumlah: " + maxPower);
				}
				
				output.newLine();
			}
			
			// Set visitted path
			map[r][c] = 'X';
			
			// Run to another path
			if(validPath(r , c-1, row, col, map))
			{
				gudakoPosition.push(new Path(r,c-1));
			}
			if(validPath(r+1,c, row, col, map))
			{
				gudakoPosition.push(new Path(r+1,c));
			}
			if(validPath(r,c+1, row, col, map))
			{
				gudakoPosition.push(new Path(r,c+1));
			}
			if(validPath(r-1,c, row, col, map))
			{
				gudakoPosition.push(new Path(r-1,c));
			}
		}
		
		// Finished
		output.write("Akhir petualangan Gudako");
		output.newLine();
		output.write("Level Gudako: " + levelGudako);
		output.newLine();
		output.write("Level pahlawan:");
		output.newLine();
		
		Collections.sort(gudakoHero, new sortHeroLevel());
		for(int i = 0; i < gudakoHero.size(); i++)
		{
			output.write(gudakoHero.get(i).name + ": " + gudakoHero.get(i).level);
			output.newLine();
		}
		
		output.flush();
		
	}
	
	private static boolean validPath(int r, int c, int row, int col, char[][] map)
	{
		return (r >= 1 && r <= row && c >= 1 && c <= col && map[r][c] != 'X' && map[r][c] != '#');
	}
}

class Path
{
	public int row;
	public int col;
	
	public Path(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Path other = (Path) obj;
		return (this.row == other.row) && (this.col == other.col);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(row, col);
	}
}

class Hero implements Comparable<Hero>
{
	public String name;
	public String weapon;
    public Integer mana;
    public Integer level;
    public Integer power;
    public Integer rank; 

    public Hero(String nama, int mana, int kekuatan, String senjata){
        this.name = nama;
        this.mana = mana;
        this.power = kekuatan;
        this.weapon = senjata;
        this.level = 1;
    }

    public int getTempPower(String foeWeapon)
    {
    	if(this.weapon.equals("pedang"))
    	{
    		if(foeWeapon.equals("panah"))
    		{
    			return this.power/2;
    		}
    	}
    	else if(this.weapon.equals("panah"))
    	{
    		if(foeWeapon.equals("pedang"))
    		{
    			return this.power*2;
    		}
    	}
    	return this.power;
    }
    
	@Override
	public int compareTo(Hero other) {
		// TODO Auto-generated method stub
		if(!this.power.equals(other.power))
		{
			return (other.power < this.power? -1:1);
		}
		else if(!this.mana.equals(other.mana))
		{
			return (this.mana < other.mana? -1:1);
		}
		return this.name.compareTo(other.name);
	}
}

class Dungeon
{
	public int power;
	public int level;
	public int maxHero;
	public String weapon;
	
	public Dungeon(int power, int level, int maxHero, String weapon) {
		this.power = power;
		this.level = level;
		this.maxHero = maxHero;
		this.weapon = weapon;
	}
}

class sortHeroesBattle implements Comparator<Hero>
{
	public String weapon;
	
	public sortHeroesBattle(String weapon)
	{
		this.weapon = weapon;
	}
	
	@Override
	public int compare(Hero hero1, Hero hero2) {
		// TODO Auto-generated method stub
		Integer power1 = hero1.getTempPower(this.weapon);
		Integer power2 = hero2.getTempPower(this.weapon);
		
		if(!power1.equals(power2))
		{
			return (power2 < power1? -1:1);
		}
		else
			return hero1.rank - hero2.rank;
	}
}

class sortHeroLevel implements Comparator<Hero>
{

	@Override
	public int compare(Hero hero1, Hero hero2) {
		// TODO Auto-generated method stub
		if(!hero1.level.equals(hero2.level))
		{
			return (hero2.level < hero1.level? -1:1);		
		}
		else if(!hero1.rank.equals(hero2.rank))
		{
			return hero1.rank - hero2.rank;
		}
		return hero1.compareTo(hero2);
	}
	
}
