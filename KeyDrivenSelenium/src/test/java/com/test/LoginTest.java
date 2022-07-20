package com.test;

import org.testng.annotations.Test;

import com.engine.keyword.KeyWordEngine;

public class LoginTest {
	public KeyWordEngine keywordengine;

	@Test
	public void loginTest() {
		
		keywordengine = new KeyWordEngine();
		keywordengine.startExecution("Sheet1");

	}

}
