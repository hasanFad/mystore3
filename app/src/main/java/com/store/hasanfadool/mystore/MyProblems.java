package com.store.hasanfadool.mystore;

public class MyProblems {

    //////////////////////////////////////////////////////////////////
   //    1          EXCEPTION                                      //
  // at insertNewUserAsync nullPointerException at line 149 but in the WS get int from the response      //
 //  at SIngInUser at line 95 > just want to check it   maybe the problem because get the response an int not a string?
    // i was changed the <void,void,string> to <void,void,Integer>
//////////////////////////////////////////////////////////////////


      ////////////////////////////////////////////////
     //     2         NOT EXCEPTION                //
    //    check the class ProductPictures         //
   //  it's ok,just because don't have any       //
  //  pictures at product_pictures table mysql  //
 ////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////
   //    // 3      NOT EXCEPTION                            //
  //     the ProgressBar                                   //
 //    at the start , when send and get any data from DB  //
///////////////////////////////////////////////////////////

   //////////////////////
  // 4 NOT EXCEPTION  //
 //    SQlLite       //
//////////////////////

     ////////////////////////////////////////////////////////////////
    // 5       NOT EXCEPTION                                       //
   //  at insert products project >> OutOfMemoryError <<         //
  //  when want to decode the pictures from bitmap to base64    //
 //  >>add some code to manifest                               //
////////////////////////////////////////////////////////////////


}
