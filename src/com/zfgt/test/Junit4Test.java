package com.zfgt.test;

import org.junit.Test;

public class Junit4Test extends Junit4Base {

	@Override
	String[] getOtherConfigs() {
		return new String[] { applicationContextFile };
	}

	@Test
	public void test() {
		System.out.println("s");
		System.out.println("e");
	}

}
