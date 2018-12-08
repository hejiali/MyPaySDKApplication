# MyPaySDKApplication
this is wallect sdk
How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  Step 2. Add the dependency
  
  
 	 dependencies {
	        implementation 'com.github.hejiali:MyPaySDKApplication:v1.2'
		}
		
		
 Step 3. activity or fragmnet use
 
 		
	String mStrOrder = "你申请的订单号";
        IntentBYManager.startLaoYuanAppPay(MainActivity.this,mStrOrder);
		
Step 4. final

	@Override
        protected void onDestroy() {
           super.onDestroy();
            IntentBYManager.destroy();
       }
