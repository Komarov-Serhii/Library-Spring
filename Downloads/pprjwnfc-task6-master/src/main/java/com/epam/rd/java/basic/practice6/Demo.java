package com.epam.rd.java.basic.practice6;

import com.epam.rd.java.basic.practice6.part1.Part1;
import com.epam.rd.java.basic.practice6.part2.Part2;
import com.epam.rd.java.basic.practice6.part3.Part3;
import com.epam.rd.java.basic.practice6.part4.Part4;
import com.epam.rd.java.basic.practice6.part5.Part5;
import com.epam.rd.java.basic.practice6.part6.Part6;
import com.epam.rd.java.basic.practice6.part6.Part61;
import com.epam.rd.java.basic.practice6.part6.Part62;
import com.epam.rd.java.basic.practice6.part6.Part63;

public class Demo {

	public static final String FILE = "part6.txt";
	public static final String INPUT = "--input";
	public static final String TASK = "--task";

	public static void main(String[] args){

		System.out.println("~~~~~~~~~~~~Part1");
		Part1.main(args);

		System.out.println("~~~~~~~~~~~~Part2");
		Part2.main(args);

		System.out.println("~~~~~~~~~~~~Part3");
		Part3.main(args);

		System.out.println("~~~~~~~~~~~~Part4");
		Part4.main(args);

		System.out.println("~~~~~~~~~~~~Part5");
		Part5.main(args);

		System.out.println("~~~~~~~~~~~~Part6");
		Part6.main(new String[]{INPUT, FILE, TASK, "frequency"});
		Part6.main(new String[]{INPUT, FILE, TASK, "length"});
		Part6.main(new String[]{INPUT, FILE, TASK, "duplicates"});

		Part61.main(args);

		Part62.main(args);

		Part63.main(args);

	}
}
