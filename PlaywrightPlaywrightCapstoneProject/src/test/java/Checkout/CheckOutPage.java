package Checkout;

import com.microsoft.playwright.*;

public class CheckOutPage {
    static String username = "standard_user";
    static String password ="secret_sauce";

    static String css_loginButton="input[type='submit'][value='Login']";
    static String id_username ="#user-name";
    static String id_password = "#password";
    static String tshirt = "#add-to-cart-sauce-labs-bolt-t-shirt";
    static String backpack ="#add-to-cart-sauce-labs-backpack";
    static String fleeceJacket = "#add-to-cart-sauce-labs-fleece-jacket";
    static String cartBtn = "#shopping_cart_container";
    static String cartContainer = "#cart_contents_container";
    static String checkoutbtn ="Checkout";

    public static void main(String[] args) {
        try(Playwright obj_playwright = Playwright.create())
        {
            Browser obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500));
            BrowserContext obj_context = obj_browser.newContext();
            Page obj_Page = obj_context.newPage();
            obj_Page.navigate("https://www.saucedemo.com");

            obj_Page.waitForTimeout(2000);
            Locator obj_UsernameLocator = obj_Page.locator(id_username);
            obj_UsernameLocator.fill(username);

            obj_Page.locator(id_password).fill(password);
            obj_Page.locator(css_loginButton).click();
            obj_Page.waitForLoadState();

            Locator obj_addTshirt = obj_Page.locator(tshirt);
            if(obj_addTshirt.isEnabled())
                System.out.println("PASS : Items 'Add to Cart' button is enabled");
            else
                System.out.println("FAIL : Items 'Add to Cart' button is not enabled");
            obj_Page.locator(tshirt).click();
            obj_Page.locator(backpack).click();
            obj_Page.locator(fleeceJacket).click();

            Locator cartBtnLocator = obj_Page.locator(cartBtn);
            if(cartBtnLocator.isVisible() && cartBtnLocator.isEnabled())
                System.out.println("PASS : Cart button is visible and enabled on dashboard!");
            else
                System.out.println("FAIL : Cart button is not visible and enabled on dashboard!");

            cartBtnLocator.click();
            obj_Page.waitForLoadState();

            Locator cart_container =obj_Page.locator(cartContainer);
            if(cart_container.isVisible())
                System.out.println("PASS : Cart list is visible");
            else
                System.out.println("FAIL : Cart list is not visible");

            //Testcase starts from here
            Locator checkoutButton =obj_Page.getByText(checkoutbtn);
            if(checkoutButton.isVisible() && checkoutButton.isEnabled())
                System.out.println("PASS : Checkout button is visible and enabled in Add to Cart!");
            else
                System.out.println("FAIL : Checkout button is not visible, not enabled in Add to Cart!");
            checkoutButton.click();

            if(obj_Page.url().contains("/checkout-step-one.html"))
                System.out.println("PASS : In checkout page!");
            else
                System.out.println("FAIL : Not in checkout page!");

            Locator checkoutTitle = obj_Page.getByText("Checkout: Your Information");
            if(checkoutTitle.isVisible())
                System.out.println("PASS : Checkout page is displayed!");
            else
                System.out.println("FAIL : Checkout page is not displayed!");

            obj_Page.getByPlaceholder("First Name").fill("ABC");
            obj_Page.getByPlaceholder("Last Name").fill("zyx");
            obj_Page.getByPlaceholder("Zip/Postal Code").fill("000000");

            Locator continueBtn = obj_Page.locator("#continue");
            if(continueBtn.isVisible() && continueBtn.isEnabled())
                System.out.println("PASS : Continue button is visible and enabled in Checkout!");
            else
                System.out.println("FAIL : Continue button is not visible, not enabled in Checkout!");
            continueBtn.click();

            Locator checkoutSummaryContainer = obj_Page.locator(".checkout_summary_container");
            if(checkoutSummaryContainer.isVisible())
                System.out.println("PASS : Checkout summary page is displayed!");
            else
                System.out.println("FAIL : Checkout summary page is not displayed!");

            Locator finishBtn = obj_Page.locator("#finish");
            if(finishBtn.isVisible() && finishBtn.isEnabled())
                System.out.println("PASS : Finish button is visible and enabled in Checkout!");
            else
                System.out.println("FAIL : Finish button is not visible, not enabled in Checkout!");
            finishBtn.click();

            Locator orderCompletionLocator = obj_Page.getByText("Thank you for your order!");
            if(orderCompletionLocator.isVisible())
                System.out.println("PASS : Order Completion message page is displayed!");
            else
                System.out.println("FAIL : Order Completion message page is not displayed!");

            obj_Page.close();
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
