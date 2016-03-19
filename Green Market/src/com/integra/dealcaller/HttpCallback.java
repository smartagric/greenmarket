package com.integra.dealcaller;
/* w  w w .j  a v  a  2 s  .c o m*/
public interface HttpCallback {
  public void onStart();
  public void onFinish();
  public void onResult(String string);
  public void onError(Exception ep); 
  public void onCancelled();
  public void onLoading(long count, long current);
}