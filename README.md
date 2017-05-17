Android Auto Scroll ViewPager
==============================
AutoViewPager which can auto scrolling, cycling, decelerating, can be slided normal in parent ViewPager.  

![Android Auto Scroll ViewPager](http://i.imgur.com/7EDictv.gif)

## Usage

Android Studio with Gradle:  
``` xml
compile 'com.zegome.utils.widget.pagers:autoviewpager:0.1.0'
```  


``` xml
<com.zegome.utils.widget.pagers.indicator.CircleIndicator
            android:id="@+id/main_indicator"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            app:indicator_fillColor="#d14007"
            app:indicator_pageColor="@android:color/darker_gray"
            app:indicator_radius="6dp"/>
```

- `startAutoScroll()` start now auto scroll, delay time is `getDuration()`.
- `startAutoScroll(long duration)` start auto scroll, delay time is `getDuration()`.
- `stopAutoScroll()` stop auto scroll. Call it if you don't want the auto scroll mode

## Setting
- `setDuration(long)` set auto scroll time in milliseconds, default is `DEFAULT_DURATION`.  
- `setStopWhenTouch(boolean)` set for stop autoscroll if TOUCH_DOWN and restart after TOUCH_UP   
- `setAutoScroll(boolean)` set auto scroll or not, default is true. 

## Contact Me
- [QuanLT::gmail.com](mailto:backviet01@gmail.com)

## License

    Copyright 2017, QuanLT

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
