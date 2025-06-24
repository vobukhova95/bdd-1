package test;

import com.codeborne.selenide.Selenide;
import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyOnFirstCard() {
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode(info);
        var cardFirst = DataHelper.getCardInfoOne();
        var cardSecond = DataHelper.getCardInfoTwo();

        LoginPage page = new LoginPage();
        var verifyPage = page.validLogin(info);
        var dashBoardPage = verifyPage.validVerify(verificationCode);
        //запрашиваем баланс карт до перевода
        int balanceFirstBefore = (int) dashBoardPage.showCardBalance(cardFirst);
        int balanceSecondBefore = (int) dashBoardPage.showCardBalance(cardSecond);
        //пополняем первую карту
        int amountTransfer = 1000;
        var transferCardPage = dashBoardPage.transferCard(cardFirst);
        // проверяем, что на странице пополнения карты поле "Куда" предзаполнено номером первой карты
        assertEquals("0001", transferCardPage.getCardTo());
        var newDashBoardPage = transferCardPage.transferAmount(cardSecond, String.valueOf(amountTransfer));
        //проверяем баланс карт после перевода
        int balanceFirstAfter = (int) newDashBoardPage.showCardBalance(cardFirst);
        int balanceSecondAfter = (int) newDashBoardPage.showCardBalance(cardSecond);
        assertEquals(balanceFirstBefore + amountTransfer, balanceFirstAfter);
        assertEquals(balanceSecondBefore - amountTransfer, balanceSecondAfter);
    }

    @Test
    void shouldTransferMoneyOnSecondCard() {
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode(info);
        var cardFirst = DataHelper.getCardInfoOne();
        var cardSecond = DataHelper.getCardInfoTwo();

        LoginPage page = new LoginPage();
        var verifyPage = page.validLogin(info);
        var dashBoardPage = verifyPage.validVerify(verificationCode);
        //запрашиваем баланс карт до перевода
        int balanceFirstBefore = (int) dashBoardPage.showCardBalance(cardFirst);
        int balanceSecondBefore = (int) dashBoardPage.showCardBalance(cardSecond);
        //пополняем вторую карту
        int amountTransfer = 1000;
        var transferCardPage = dashBoardPage.transferCard(cardSecond);
        // проверяем, что на странице пополнения карты поле "Куда" предзаполнено номером первой карты
        assertEquals("0002", transferCardPage.getCardTo());
        var newDashBoardPage = transferCardPage.transferAmount(cardFirst, String.valueOf(amountTransfer));
        //проверяем баланс карт после перевода
        int balanceFirstAfter = (int) newDashBoardPage.showCardBalance(cardFirst);
        int balanceSecondAfter = (int) newDashBoardPage.showCardBalance(cardSecond);
        assertEquals(balanceFirstBefore - amountTransfer, balanceFirstAfter);
        assertEquals(balanceSecondBefore + amountTransfer, balanceSecondAfter);
    }
}
