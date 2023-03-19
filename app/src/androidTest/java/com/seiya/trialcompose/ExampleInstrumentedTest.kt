package com.seiya.trialcompose

import androidx.compose.ui.test.*
import org.junit.Test
import androidx.compose.ui.test.junit4.createComposeRule
import com.seiya.trialcompose.compose.*
import org.junit.Rule
import org.junit.Assert.*

class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    //Navigationによる画面遷移
    @Test
    fun navigationTest() {
        composeTestRule.setContent {
            NavigationTop()
        }

        // 初期画面でTop画面が表示されるか
        composeTestRule.onNodeWithText("リスト表示").assertExists()

        //"カウントアップ"ボタンをクリックし、Screen1画面に遷移するか
        composeTestRule.onNodeWithText("カウントアップ").performClick()
        composeTestRule.onNodeWithText("Count").assertExists()

        // "Back"ボタンをクリックし、Top画面に戻るか
        composeTestRule.onNodeWithText("Back").performClick()
        composeTestRule.onNodeWithText("リスト表示").assertExists()

        //"リスト表示"ボタンをクリックし、Screen2画面に遷移するか
        composeTestRule.onNodeWithText("リスト表示").performClick()
        composeTestRule.onNodeWithText("Tom").assertExists()

        // "Back"ボタンをクリックし、Top画面に戻るか
        composeTestRule.onNodeWithText("Back").performClick()
        composeTestRule.onNodeWithText("リスト表示").assertExists()
    }

    //トップ画面
    @Test
    fun topScreenButtonsDisplayed() {
        // topScreenをレンダリング
        composeTestRule.setContent {
            topScreen(onClickButton = {})
        }

        // "カウントアップ"というテキストが表示されているか
        composeTestRule.onNodeWithText("カウントアップ").assertExists()

        // "リスト表示"というテキストが表示されているか
        composeTestRule.onNodeWithText("リスト表示").assertExists()
    }
    @Test
    fun topScreenOnClickButton() {
        var buttonClicked = 0
        val onClickButton: (Int) -> Unit = { buttonClicked = it }
        composeTestRule.setContent { topScreen(onClickButton) }

        // "カウントアップ"ボタンをクリックして、onClickButtonが呼び出されることを確認
        composeTestRule.onNodeWithText("カウントアップ").performClick()
        assertEquals(1,buttonClicked)

        // "リスト表示"ボタンをクリックして、onClickButtonが呼び出されることを確認
        composeTestRule.onNodeWithText("リスト表示").performClick()
        assertEquals(2,buttonClicked)
    }

    //カウントアップ画面
    @Test
    fun clickCountButtonUpdatesCount() {
        composeTestRule.setContent {
            Screen1(onClickButton = {})
        }

        //「Count」ボタンをクリック
        composeTestRule.onNodeWithText("Count").performClick()

        //2に更新されたことを確認
        composeTestRule.onNodeWithText("Count: 2").assertExists()
    }
    @Test
    fun clickClearButton_clearsCount() {
        composeTestRule.setContent {
            Screen1(onClickButton = {})
        }

        //「Count」ボタンを三回クリック
        for(i in 1..3) {
            composeTestRule.onNodeWithText("Count").performClick()
        }
        //6に更新されたことを確認
        composeTestRule.onNodeWithText("Count: 6").assertExists()

        //「Clear」ボタンをクリック
        composeTestRule.onNodeWithText("Clear").performClick()
        //0にリセットされたことを確認
        composeTestRule.onNodeWithText("Count: 0").assertExists()
    }

    //リスト表示画面
    @Test
    fun alertDialogIsDisplayedOnItemClick() {
        composeTestRule.setContent {
            Screen2(onClickButton = {})
        }

        //「Bob」アイテムをクリック
        val expectedText = "Index 1 is clicked."
        composeTestRule.onNodeWithText("Bob").performClick()
        //該当ダイアログが表示されているか確認
        composeTestRule.onNodeWithText(expectedText).assertExists()

        // ダイアログの OK ボタンをクリックしてダイアログを閉じる
        composeTestRule.onNodeWithText("OK").performClick()
        // AlertDialog が消えたことを確認する
        composeTestRule.onNodeWithText(expectedText).assertDoesNotExist()
    }
}