package test;

import com.codeborne.selenide.Selenide;
import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import java.util.Random;

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
        var balanceFirstBefore = dashBoardPage.showCardBalance(cardFirst);
        var balanceSecondBefore = dashBoardPage.showCardBalance(cardSecond);
        //пополняем первую карту
        Random random = new Random();
        var amountTransfer = 1 + random.nextInt(balanceSecondBefore);
        var transferCardPage = dashBoardPage.transferCard(cardFirst);
        var newDashBoardPage = transferCardPage.transferAmount(cardSecond, String.valueOf(amountTransfer));
        //проверяем баланс карт после перевода
        var expectedBalanceFirstAfter = balanceFirstBefore + amountTransfer;
        var expectedBalanceSecondAfter = balanceSecondBefore - amountTransfer;
      newDashBoardPage.showCardBalanceAfterTransfer(cardFirst, expectedBalanceFirstAfter);
      newDashBoardPage.showCardBalanceAfterTransfer(cardSecond, expectedBalanceSecondAfter);

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
        var balanceFirstBefore = dashBoardPage.showCardBalance(cardFirst);
        var balanceSecondBefore = dashBoardPage.showCardBalance(cardSecond);
        //пополняем вторую карту
        Random random = new Random();
        var amountTransfer = 1 + random.nextInt(balanceFirstBefore);
        var transferCardPage = dashBoardPage.transferCard(cardSecond);
        var newDashBoardPage = transferCardPage.transferAmount(cardFirst, String.valueOf(amountTransfer));
        //проверяем баланс карт после перевода
        var expectedBalanceFirstAfter = balanceFirstBefore - amountTransfer;
        var expectedBalanceSecondAfter = balanceSecondBefore + amountTransfer;
        newDashBoardPage.showCardBalanceAfterTransfer(cardFirst, expectedBalanceFirstAfter);
        newDashBoardPage.showCardBalanceAfterTransfer(cardSecond, expectedBalanceSecondAfter);
    }
}
