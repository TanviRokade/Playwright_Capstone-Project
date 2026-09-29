package Login;

import com.microsoft.playwright.*;

public class LoginPageMainInvalidInput {
    static String username = "ABC";
    static String password ="xyz";

    static String css_loginButton="input[type='submit'][value='Login']";
    static String id_username ="#user-name";
    static String id_password = "#password";

    public static void main(String[] args) {
        try(Playwright obj_playwright = Playwright.create())
        {
            Browser obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext obj_context = obj_browser.newContext();
            Page obj_Page = obj_context.newPage();
            obj_Page.navigate("https://www.saucedemo.com");

            obj_Page.waitForTimeout(2000);
            Locator obj_UsernameLocator = obj_Page.locator(id_username);
            obj_UsernameLocator.fill(username);

            obj_Page.locator(id_password).fill(password);
            obj_Page.locator(css_loginButton).click();
            obj_Page.waitForLoadState();

            Locator errorBtn = obj_Page.getByTestId("error-button");

            if(errorBtn.isVisible())
                System.out.println("PASS : Invalid credentials are not allowed login!");
            else
                System.out.println("FAIL : Invalid credentials are accepted");

            obj_Page.close();

        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
