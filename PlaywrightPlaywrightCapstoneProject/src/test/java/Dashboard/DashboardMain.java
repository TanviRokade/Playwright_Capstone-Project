package TestNGSauceDemoWithPlayWright;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DashboardMain {
    Playwright obj_playwright;
    Browser obj_browser;
    BrowserContext obj_context;
    Page obj_Page;

    static String username = "standard_user";
    static String password ="secret_sauce";
    static String css_loginButton="input[type='submit'][value='Login']";
    static String id_username ="#user-name";
    static String id_password = "#password";
    static String inventory_container = "[data-test='inventory-container']";
    static String filterDropdown ="[data-test='product-sort-container']";
    static String filterAtoZ = "Name (A to Z)";
    static String itemInList = "[data-test='inventory-item']";

    @BeforeMethod
    public void setUp(){
        obj_playwright = Playwright.create();
        obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--start-maximized")));
        obj_context = obj_browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        obj_Page = obj_context.newPage();
    }
    @Test
    public void validateDashBoard()
    {

        obj_Page.navigate("https://www.saucedemo.com");
        obj_Page.waitForTimeout(2000);
        Locator obj_UsernameLocator = obj_Page.locator(id_username);
        obj_UsernameLocator.fill(username);

        obj_Page.locator(id_password).fill(password);
        obj_Page.locator(css_loginButton).click();
        obj_Page.waitForLoadState();

        if(obj_Page.url().contains("inventory.html"))
            System.out.println("PASS : Successfully logged in!");
        else
            System.out.println("FAIL : Invalid credentials!");

        Locator inventory_containerLocator =obj_Page.locator(inventory_container);
        if(inventory_containerLocator.isVisible())
            System.out.println("PASS : Products list visible on dashboard!");
        else
            System.out.println("FAIL : Products list  are not visible on dashboard!");

        Locator item = obj_Page.locator(itemInList).first();
        if(item.isVisible())
            System.out.println("PASS : Item in list is visible on dashboard!");
        else
            System.out.println("FAIL : Item in list is not visible on dashboard!");

        Locator filterLocator =obj_Page.locator(filterDropdown);
        if(filterLocator.isVisible())
            System.out.println("PASS : Filter visible on dashboard!");
        else
            System.out.println("FAIL : Filter is not visible on dashboard!");
        filterLocator.selectOption(filterAtoZ);
        filterLocator.waitFor();
        String selectedValue =filterLocator.locator("option:checked").textContent();
        System.out.println(selectedValue);
        if(selectedValue.equals(filterAtoZ))
            System.out.println("PASS : Filter option is working!");
        else
            System.out.println("FAIL : Filter option is not working!");
    }


    @AfterMethod
    public void teardown()
    {
        obj_Page.close();
    }
}
