package com.example.dpsadmin

class HtmlString {
    companion object{

        var pdfStr = ""

        fun getHtmlForPdf(srNo:Int,date:String,cls:String, name:String, admissionFee:Int,annualFee:Int,tuitionFee:Int
            ,computerFee:Int,transportFee:Int, examinationFee:Int,
            supplementaryFee:Int,bookFee:Int, lateFee:Int, total:Int,month:String):String{

            return "<!DOCTYPE html>\n" +
                    "<head>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      margin-top: 50px;\n" +
                    "    }\n" +
                    "    .h1 {\n" +
                    "      color: black;\n" +
                    "      -webkit-text-fill-color: white;\n" +
                    "      -webkit-text-stroke-width: 1px;\n" +
                    "      -webkit-text-stroke-color: black;\n" +
                    "      font-size: 2rem;\n" +
                    "    }\n" +
                    "    .h3 {\n" +
                    "      font-size: 1.2rem;\n" +
                    "    }\n" +
                    "    .bold {\n" +
                    "      font-weight: bold;\n" +
                    "    }\n" +
                    "\n" +
                    "    .center {\n" +
                    "      text-align: center;\n" +
                    "    }\n" +
                    "    .left {\n" +
                    "      text-align: left;\n" +
                    "    }\n" +
                    "    .right {\n" +
                    "      text-align: right;\n" +
                    "    }\n" +
                    "    .right-doted {\n" +
                    "      border-right: 1px dotted black;\n" +
                    "    }\n" +
                    "    .dottedUnderline {\n" +
                    "      /* border-bottom: 1px dotted; */\n" +
                    "    }\n" +
                    "    .s-fee {\n" +
                    "      background-color: #333;\n" +
                    "      color: #fff;\n" +
                    "      font-style: italic;\n" +
                    "      padding-left: 50px;\n" +
                    "    }\n" +
                    "    .border-right {\n" +
                    "      border-right: 1px solid black;\n" +
                    "    }\n" +
                    "\n" +
                    "    .border-top {\n" +
                    "      border-top: 1px solid black;\n" +
                    "    }\n" +
                    "    .p-bottom {\n" +
                    "      padding-bottom: 8px;\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <table style=\"margin: auto\">\n" +
                    "    <tr>\n" +
                    "      <td>\n" +
                    "        <table width=\"400px\" style=\"border: 1px solid black; margin: auto\">\n" +
                    "          <tr>\n" +
                    "            <td rowspan=\"3\">\n" +
                    "              <img\n" +
                    "                width=\"100px\"\n" +
                    "                src=\"https://firebasestorage.googleapis.com/v0/b/pankajoil.appspot.com/o/icon.jpg?alt=media&token=c8a69671-adb4-4a3c-9717-2f16c502b8ed\"\n" +
                    "                alt=\" logo \"\n" +
                    "              />\n" +
                    "            </td>\n" +
                    "            <td colspan=\"2\" class=\"h1 center\">\n" +
                    "              Divine Public School\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td colspan=\"2\" class=\"h3 center\">\n" +
                    "              Satyanganij, Ahraura, Mirzapur\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"left\">Sr. No. : <span class=\"bold\">${srNo}</span></td>\n" +
                    "            <td>Date : <span class=\"bold\">${date}</span></td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td colspan=\"2\">\n" +
                    "              Name :\n" +
                    "              <span class=\"bold\">${name}</span>\n" +
                    "            </td>\n" +
                    "            <td>Class : <span class=\"bold\">${cls}</span></td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"s-fee\" colspan=\"4\">School Fee</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              1. Admission Charge\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${admissionFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">2. Tution Fee</td>\n" +
                    "            <td class=\"right\">${tuitionFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">3. Computer Fee</td>\n" +
                    "            <td class=\"right\">${computerFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">4. Conveyance Fee</td>\n" +
                    "            <td class=\"right\">${transportFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              5. Examination Fee\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${examinationFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              6. Annual Fee\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${annualFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              7. Medicine Charges\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">0&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              8. Supplementary Fee\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${supplementaryFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">9. Books Fee</td>\n" +
                    "            <td class=\"right\">${bookFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">10. Late Fee</td>\n" +
                    "            <td class=\"right\">${lateFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right border-top\" colspan=\"2\">\n" +
                    "              <span class=\"h3\">Month(s): </span>\n" +
                    "              <span>${month}</span>\n" +
                    "            </td>\n" +
                    "            <td class=\"border-top\">\n" +
                    "              <span class=\"h3\">Total = </span>\n" +
                    "              <span class=\"bold\">₹ ${total}&emsp;&emsp;</span>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "\n" +
                    "        <table width=\"400px\" style=\"margin: auto\">\n" +
                    "          <tr>\n" +
                    "            <td>School's Copy</td>\n" +
                    "            <td class=\"right\">\n" +
                    "              Signature :\n" +
                    "              <span class=\"dottedUnderline\"></span>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "      </td>\n" +
                    "      <td class=\"right-doted\" style=\"color: white\">&emsp;&emsp;&emsp;</td>\n" +
                    "      <td style=\"color: white\">&emsp;&emsp;&emsp;</td>\n" +
                    "      <td>\n" +
                    "        <table width=\"400px\" style=\"border: 1px solid black; margin: auto\">\n" +
                    "          <tr>\n" +
                    "            <td rowspan=\"3\">\n" +
                    "              <img\n" +
                    "                width=\"100px\"\n" +
                    "                src=\"https://firebasestorage.googleapis.com/v0/b/pankajoil.appspot.com/o/icon.jpg?alt=media&token=c8a69671-adb4-4a3c-9717-2f16c502b8ed\"\n" +
                    "                alt=\" logo \"\n" +
                    "              />\n" +
                    "            </td>\n" +
                    "            <td colspan=\"2\" class=\"h1 center\">\n" +
                    "              Divine Public School\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td colspan=\"2\" class=\"h3 center\">\n" +
                    "              Satyanganij, Ahraura, Mirzapur\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"left\">Sr. No. : <span class=\"bold\">${srNo}</span></td>\n" +
                    "            <td>Date : <span class=\"bold\">${date}</span></td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td colspan=\"2\">\n" +
                    "              Name :\n" +
                    "              <span class=\"bold\">${name}</span>\n" +
                    "            </td>\n" +
                    "            <td>Class : <span class=\"bold\">${cls}</span></td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"s-fee\" colspan=\"4\">School Fee</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right  p-bottom\" colspan=\"2\">\n" +
                    "              1. Admission Charge\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${admissionFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">2. Tution Fee</td>\n" +
                    "            <td class=\"right\">${tuitionFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">3. Computer Fee</td>\n" +
                    "            <td class=\"right\">${computerFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">4. Conveyance Fee</td>\n" +
                    "            <td class=\"right\">${transportFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              5. Examination Fee\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${examinationFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              6. Annual Fee\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${annualFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              7. Medicine Charges\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">0&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">\n" +
                    "              8. Supplementary Fee\n" +
                    "            </td>\n" +
                    "            <td class=\"right\">${supplementaryFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right p-bottom\" colspan=\"2\">9. Book Fee</td>\n" +
                    "            <td class=\"right\">${bookFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right\" colspan=\"2\">10. Late Fee</td>\n" +
                    "            <td class=\"right\">${lateFee}&emsp;&emsp;&emsp;</td>\n" +
                    "          </tr>\n" +
                    "\n" +
                    "          <tr>\n" +
                    "            <td class=\"border-right border-top\" colspan=\"2\">\n" +
                    "              <span class=\"h3\">Month(s): </span>\n" +
                    "              <span>${month}</span>\n" +
                    "            </td>\n" +
                    "            <td class=\"border-top\">\n" +
                    "              <span class=\"h3\">Total = </span>\n" +
                    "              <span class=\"bold right\">₹ ${total}</span>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "\n" +
                    "        <table width=\"400px\" style=\"margin: auto\">\n" +
                    "          <tr>\n" +
                    "            <td>Parent's Copy</td>\n" +
                    "            <td class=\"right\">\n" +
                    "              Signature : <span class=\"dottedUnderline\"></span>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </table>\n" +
                    "</body>"

        }


    }
}