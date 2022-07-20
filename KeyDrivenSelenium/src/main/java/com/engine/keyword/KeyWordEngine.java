package com.engine.keyword;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.keyword.base.Base;

public class KeyWordEngine {

	public WebDriver driver;
	public Properties prop;

	public Base base;
	public WebElement element;

	public static Workbook book;
	public static Sheet sheet;

	public final String SCENARIO_SHEET_PATH = "C:\\Users\\PC\\eclipse-workspace\\KeyDrivenSelenium\\src\\main\\java\\com\\scenarios\\xlx\\KeyWords.xlsx";

	public void startExecution(String sheetName) {

		FileInputStream file = null;
		String locatorName = null;
		String locatorValue = null;

		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
			String locatorCalValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim(); // id=username
			if (!locatorCalValue.equalsIgnoreCase("NA")) {
				locatorName = locatorCalValue.split("=")[0].trim(); // id
				locatorValue = locatorCalValue.split("=")[1].trim(); // username
			}

			String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
			String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

			switch (action) {
			case "open browser":
				base = new Base();
				prop = base.init_properties();
				if (value.isEmpty() || value.equals("NA")) {
					driver = base.inti_driver(prop.getProperty("browser"));
				} else {
					driver = base.inti_driver(value);
				}

				break;

			case "enter url":
				if (value.isEmpty() || value.equals("NA")) {
					driver.get(prop.getProperty("url"));
				} else {
					driver.get(value);
				}

			case "quit":
				driver.quit();
				break;

			default:
				break;
			}

			switch (locatorName) {
			case "id":
				element = driver.findElement(By.id(locatorValue));
				if (action.equalsIgnoreCase("sendkeys")) {
					element.sendKeys(value);
					element.clear();
				} else if (action.equalsIgnoreCase("click")) {
					element.click();
				}
				locatorName = null;
				break;

			case "xpath":
				element = driver.findElement(By.xpath(locatorValue));
				element.click();
				locatorName = null;
			default:
				break;

			}
			} catch(Exception e) {
				
			}

		}

	}

}
