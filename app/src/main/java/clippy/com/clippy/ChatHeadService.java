package clippy.com.clippy;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatHeadService extends Service {

    private WindowManager mWindowManager;
    private View mChatHeadView;

    public ChatHeadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the chat head layout we created
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the chat head position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 0;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mChatHeadView, params);

        hideViews();

        ////Set the close button.
        //ImageView closeButton = (ImageView) mChatHeadView.findViewById(R.id.close_btn);
        //closeButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        //close the service and remove the chat head from the window
        //        stopSelf();
        //    }
        //});
        final ImageView clippyBackground = (ImageView) mChatHeadView.findViewById(R.id.clippy_background);

        clippyBackground.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //chatHead.setX(event.getRawX());
                        //chatHead.setY(event.getRawY());
                        //clippy.setX(mChatHeadView.getWidth());
                        //clippy.setVisibility(View.VISIBLE);
                        startAction("It looks like you want to open an app, would you like some help with that?");
                        //mWindowManager.updateViewLayout(mChatHeadView, params);
                        return true;
//                    case MotionEvent.ACTION_UP:
//                        Intent intent = new Intent(ChatHeadService.this, ChatActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//
//                        //close the service and remove the chat heads
//                        stopSelf();
//                        return true;
                }
                return false;
            }
        });
        /*
        //Drag and move chat head using user's touch action.
        chatHeadImage.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //As we implemented on touch listener with ACTION_MOVE,
                        //we have to check if the previous action was ACTION_DOWN
                        //to identify if the user clicked the view or not.
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            //Open the chat conversation click.
                            Intent intent = new Intent(ChatHeadService.this, ChatActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            //close the service and remove the chat heads
                            stopSelf();
                        }
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mChatHeadView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });
        */
    }

    private void startAction(String question) {
        final ImageView clippy = mChatHeadView.findViewById(R.id.clippy);
        float screenWidth = mChatHeadView.getWidth();
        clippy.setVisibility(View.VISIBLE);
        clippy.setX(screenWidth - clippy.getWidth());

        final TextView message = mChatHeadView.findViewById(R.id.message);
        message.setVisibility(View.VISIBLE);
        message.setText(question);
        final TextView yes = mChatHeadView.findViewById(R.id.yes);
        yes.setVisibility(View.VISIBLE);
        final TextView no = mChatHeadView.findViewById(R.id.no);
        no.setVisibility(View.VISIBLE);
//        for (int i = 0; i < clippy.getWidth(); i++) {
//            clippy.setX(screenWidth - i);
//
//        }

        //mWindowManager.updateViewLayout(mChatHeadView, params);

    }
    private void hideViews() {
        mChatHeadView.findViewById(R.id.clippy).setVisibility(View.INVISIBLE);
        mChatHeadView.findViewById(R.id.message).setVisibility(View.INVISIBLE);
        mChatHeadView.findViewById(R.id.yes).setVisibility(View.INVISIBLE);
        mChatHeadView.findViewById(R.id.no).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
    }
}
