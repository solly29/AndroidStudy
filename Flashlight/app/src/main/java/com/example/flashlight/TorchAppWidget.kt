package com.example.flashlight

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class TorchAppWidget : AppWidgetProvider() {
    // 위젯이 업데이트되어야 할 때 호출
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // 위젯이 여러개이면 모두 업데이트
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // 위젯이 처음 생성될때 호출
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    // (여러개일때)마지막 위젯이 제거될때 호출
    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

// 위젯을 업데이트할때 호출
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // 위젯의 뷰를 가지고온다.(전체 레이아웃)
    val views = RemoteViews(context.packageName, R.layout.torch_app_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText) // 위젯의 텍스트 변경

    // 여기서 부터 위젯기능부분
    val intent = Intent(context, TorchService::class.java)
    val pendingIntent = PendingIntent.getService(context, 0, intent, 0) // 서비스 인텐트 실행 하도록한다.
    // getActivity - 액티비티 실행
    // getV\Broadcast() - 브로드캐스트 실행

    // 위젯을 클릭하면 위에서 정의한 intent실행
    views.setOnClickPendingIntent(R.id.appWidget_layout, pendingIntent) // 실행할 뷰의 id와 PendingIntent 객체

    // 위젯 업데이트
    appWidgetManager.updateAppWidget(appWidgetId, views)
}