package com.pinguinson.sudoku.engine;

import java.util.ArrayList;
import java.util.Random;

public class TaskGenerator {
    private String[][] tasks;
    private String[][] solutions;
    private int[] converter;
    private Random random;
    private int version;
    private int timesMirror;
    private int timesRotate;

    public static final int DIFFICULTY_BEGINNER = 0;
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;
    public static final int DIFFICULTY_EVIL = 4;

    public TaskGenerator() {
        tasks = new String[5][10];
        solutions = new String[5][10];
        converter = generateConverter();
        random = new Random();
        timesMirror = random.nextInt(2);
        timesRotate = random.nextInt(4);
        version = random.nextInt(10);

        tasks[0][0] = "EI@B@@GA@AHB@@G@C@GD@I@@EHBHBA@D@@ICIF@H@@D@A@CG@@ABEHCGDAF@H@EBA@@G@CFIF@@CH@A@G";
        solutions[0][0] = "EIFBCHGADAHBDEGICFGDCIAFEHBHBAGDEFICIFEHBCDGADCGFIABEHCGDAFIHBEBAHEGDCFIFEICHBADG";

        tasks[0][1] = "CDGH@EF@A@BHI@F@GDIEFDGAB@HGC@E@B@@@B@@CFIG@@FI@ADG@@@H@BF@CD@G@FIGADC@B@@C@E@I@@";
        solutions[0][1] = "CDGHBEFIAABHICFEGDIEFDGABCHGCDEHBAFIBHACFIGDEFIEADGHBCHABFICDEGEFIGADCHBDGCBEHIAF";

        tasks[0][2] = "@@EH@@DGA@@C@AFEHBBAH@@DCF@EIF@@H@@@D@GF@A@E@HBA@DEFI@CGD@@I@BEF@ICH@A@@A@@D@G@@F";
        solutions[0][2] = "IFEHBCDGAGDCIAFEHBBAHEGDCFIEIFBCHGADDCGFIABEHHBAGDEFICCGDAFIHBEFEICHBADGAHBDEGICF";

        tasks[0][3] = "@C@AF@B@GB@DHCGAFEA@G@@EH@DCEID@FGB@DH@G@IC@F@BFC@@@H@E@HFGCIA@IA@@DBF@@@GBIAHED@";
        solutions[0][3] = "HCEAFDBIGBIDHCGAFEAFGBIEHCDCEIDHFGBADHAGBICEFGBFCEADHIEDHFGCIABIACEDBFGHFGBIAHEDC";

        tasks[0][4] = "I@BGAE@H@FGH@@BAECADE@FH@BG@IGF@ABD@BF@@CGHA@@@AI@@CGFG@C@D@E@@DB@AEIGC@E@IBGCD@@";
        solutions[0][4] = "ICBGAEFHDFGHDIBAECADECFHIBGCIGFHABDEBFDECGHAIHEAIBDCGFGACHDFEIBDBFAEIGCHEHIBGCDFA";

        tasks[0][5] = "EF@B@@@IH@@GEDF@ABBA@@GI@FEF@BC@DH@I@GH@B@AD@CDAIH@@@F@@EG@H@CD@@@@@CE@A@CF@EBI@G";
        solutions[0][5] = "EFDBCAGIHHIGEDFCABBACHGIDFEFEBCADHGIIGHFBEADCCDAIHGBEFABEGIHFCDGHIDFCEBADCFAEBIHG";

        tasks[0][6] = "@@FACGD@@HGI@@@@ACAD@@@EG@FG@@DEAF@BCABG@@ID@@FECBI@@HFC@EAH@IGI@@F@@CEA@B@@G@HFD";
        solutions[0][6] = "BEFACGDHIHGIBFDEACADCHIEGBFGIHDEAFCBCABGHFIDEDFECBIAGHFCDEAHBIGIHGFDBCEAEBAIGCHFD";

        tasks[0][7] = "BCH@@IADGAF@CDG@EHDE@@@HCFI@H@FIE@@AEGDB@@@I@IA@@CDHBEF@@DGC@@@@@E@@@IC@@B@IEF@AD";
        solutions[0][7] = "BCHEFIADGAFICDGBEHDEGABHCFICHBFIEDGAEGDBHAFICIAFGCDHBEFIADGCEHBGDEHABICFHBCIEFGAD";

        tasks[0][8] = "FICHBA@DE@G@@FEHBC@BE@GDA@IBEHDCGFI@@DG@EICH@@CF@@BDE@@F@B@@@@@GAD@I@@C@E@@GDC@AF";
        solutions[0][8] = "FICHBAGDEDGAIFEHBCHBECGDAFIBEHDCGFIAADGFEICHBICFAHBDEGCFIBAHEGDGADEIFBCHEHBGDCIAF";

        tasks[0][9] = "EC@@IBFDHGF@@C@I@@D@HE@ACGB@BCI@DEAGI@@A@GH@CAHG@E@BI@@AEH@@@@I@D@@@IGCE@GIC@E@HF";
        solutions[0][9] = "ECAGIBFDHGFBDCHIEADIHEFACGBFBCIHDEAGIEDABGHFCAHGFECBIDCAEHGFDBIHDFBAIGCEBGICDEAHF";

        tasks[1][0] = "@@AC@GBD@E@DHIA@GF@CG@E@@@@BEI@@@@C@@@@@HC@I@@@CE@ID@@GI@@D@@@@D@@AC@IB@@@@@@B@HD";
        solutions[1][0] = "IHACFGBDEEBDHIACGFFCGBEDHAIBEIDAFGCHADFGHCEIBHGCEBIDFAGIBFDHAECDFHACEIBGCAEIGBFHD";

        tasks[1][1] = "A@@@@@@F@CGD@A@@@BEI@@BHGDA@D@@IF@BH@@@@H@D@@@@A@GE@CI@AHGED@@@@@G@@@BH@@@I@CB@@@";
        solutions[1][1] = "AHBEDGIFCCGDFAIHEBEIFCBHGDAGDCAIFEBHIFEBHCDAGHBADGEFCIBAHGEDCIFDCGIFABHEFEIHCBAGD";

        tasks[1][2] = "IF@@DG@E@E@@H@B@@I@BHI@@@AG@@C@I@@G@FA@@@@@@HGE@AB@@FCD@E@@A@@@HCB@FIG@@A@F@G@@H@";
        solutions[1][2] = "IFACDGHEBEDGHABFCICBHIEFDAGBHCFIEAGDFAIGCDEBHGEDABHIFCDGEBHACIFHCBEFIGDAAIFDGCBHE";

        tasks[1][3] = "C@F@AE@B@@AI@D@@@@B@@C@@@HF@@@@HG@ID@@GA@D@FCFHD@@@E@@@I@G@@@@HGCHDF@I@AD@AEI@@GB";
        solutions[1][3] = "CGFHAEDBIHAIBDFGCEBDECGIAHFAECFHGBIDIBGAEDHFCFHDIBCEAGEIBGCAFDHGCHDFBIEADFAEIHCGB";

        tasks[1][4] = "@G@@AHC@FH@@I@E@@@@IA@@G@@E@HB@@I@@D@F@CG@@EB@@@A@@I@@@DEH@@F@IIAF@@@@@@BCH@I@G@A";
        solutions[1][4] = "EGDBAHCIFHBCIFEDAGFIADCGBHECHBFEIAGDAFICGDHEBDEGAHBIFCGDEHBAFCIIAFGDCEBHBCHEIFGDA";

        tasks[1][5] = "@@E@C@@DA@@D@@@@@HAIG@FH@E@@D@HAF@B@@@H@DI@@@@@@@GEDHI@@A@HCE@@G@@@@D@AC@@IA@@BFD";
        solutions[1][5] = "HFEGCBIDABCDEIAFGHAIGDFHCEBIDCHAFGBEEGHBDIACFFABCGEDHIDBAFHCEIGGEFIBDHACCHIAEGBFD";

        tasks[1][6] = "@@@@HEI@@EFI@D@CB@A@@I@@@D@F@EA@D@@BBH@CIFG@DC@G@EBF@I@EFD@@@HC@@@F@ID@E@C@@B@@@@";
        solutions[1][6] = "DGCBHEIFAEFIGDACBHABHIFCEDGFIEAGDHCBBHACIFGEDCDGHEBFAIIEFDAGBHCHABFCIDGEGCDEBHAIF";

        tasks[1][7] = "@F@C@GBHE@@@HAB@F@@C@@F@AG@FAIG@@H@B@G@B@AFC@@@@@IE@AGC@H@E@G@A@@@@@@EB@@@D@BHCIF";
        solutions[1][7] = "IFACDGBHEEDGHABIFCHCBEFIAGDFAIGCDHEBDGEBHAFCIBHCFIEDAGCBHIEFGDAAIFDGCEBHGEDABHCIF";

        tasks[1][8] = "D@A@@@@IB@BFIHCD@@CE@@B@GF@@G@@@I@@@@@H@@@@@GI@CHG@F@D@@GD@@H@@@CEGI@BDF@I@EFHA@C";
        solutions[1][8] = "DHAFEGCIBGBFIHCDAECEIABDGFHFGBCDIEHAEDHBAFICGIACHGEFBDAFGDCBHEIHCEGIABDFBIDEFHAGC";

        tasks[1][9] = "@BGF@EC@@@HD@@@@@@@E@I@HDABG@B@@@@@F@@@@H@@@C@C@E@@@@@BA@@@D@HGHGF@EAI@D@@EHIGF@A";
        solutions[1][9] = "ABGFDECIHIHDACBGFEFECIGHDABGIBDACHEFEFAGHIBDCDCHEBFAGIBAICFDEHGHGFBEAICDCDEHIGFBA";

        tasks[2][0] = "@D@@@I@@AI@@@HEC@D@@@@@FHI@@@@@E@@@@A@@@@@@@I@@DC@AEB@C@FE@D@GHG@ABI@@@@@@IH@G@@@";
        solutions[2][0] = "FDHGCIBEAIGBAHECFDEACDBFHIGBFGIEHDACACEFDBGHIHIDCGAEBFCBFEADIGHGHABICFDEDEIHFGACB";

        tasks[2][1] = "@@@@E@@FB@B@@@@C@G@ACFB@I@ECFD@IBA@@@H@C@@@B@B@@@@H@@@IG@@@@@@@FD@@GAB@I@@@E@@@@F";
        solutions[2][1] = "HIGAECDFBEBFIHDCAGDACFBGIHECFDGIBAEHGHICAEFBDBEADFHGICIGHBCFEDAFDEHGABCIACBEDIHGF";

        tasks[2][2] = "@CH@BF@@@@GE@@A@FB@@@G@@C@H@F@I@@E@@H@@EGB@@@BEG@AC@@@@@@@@@@@@@@C@@@ADI@@@BCE@G@";
        solutions[2][2] = "ACHDBFGIEIGECHADFBFDBGEICAHCFAIDHEBGHIDEGBFCABEGFACIHDGHFAIDBECEBCHFGADIDAIBCEHGF";

        tasks[2][3] = "@@@@I@@D@@@G@@DB@IE@@@A@@@FD@F@B@G@@@@@DC@@@@@@@@HI@@B@GB@@A@HE@EAIGH@@D@@@FEBCAG";
        solutions[2][3] = "BFCHIGEDAHAGEFDBCIEIDBACHGFDHFABEGICGBIDCFAEHACEGHIDFBFGBCDAIHECEAIGHFBDIDHFEBCAG";

        tasks[2][4] = "B@@@AGIEFE@@@@CA@H@I@H@@C@@@@BGDAE@@@@G@C@@@@@@@EB@@@DG@@@IFB@@@BC@@DF@@@@F@H@@@C";
        solutions[2][4] = "BCHDAGIEFEGDIFCABHFIAHEBCDGCHBGDAEFIDEGFCIHABAFIEBHGCDGDECIFBHAHBCAGDFIEIAFBHEDGC";

        tasks[2][5] = "@@FC@GH@@@HB@@@@GA@D@@BH@@@@@C@FI@A@@E@@@@@@I@@@G@D@@BA@@@@C@@H@C@@@E@@GEGDH@@@IC";
        solutions[2][5] = "IAFCDGHBECHBIEFDGAGDEABHICFHBCEFIGADDEGBHACFIFIAGCDEHBAFIDGCBEHBCHFIEADGEGDHABFIC";

        tasks[2][6] = "@@@I@F@D@@E@A@@C@@@@@@@G@@E@@EB@AF@I@AIG@@H@@BH@@@EDA@@@F@@C@@@E@G@@@@@C@@B@@IAGD";
        solutions[2][6] = "CBHIEFGDAGEDABHCIFIFACDGBHEDGEBHAFCIFAIGCDHEBBHCFIEDAGAIFDGCEBHEDGHABIFCHCBEFIAGD";

        tasks[2][7] = "@@@@HFIG@@@@C@@@@D@@@GB@EA@@@EI@H@@@I@H@C@@D@@@DF@B@@@CB@@ID@@F@EF@@G@B@@@IB@C@EA";
        solutions[2][7] = "EABDHFIGCHIGCEABFDFDCGBIEAHBFEIDHACGIGHACEFDBACDFGBHIECBAEIDGHFDEFHAGCBIGHIBFCDEA";

        tasks[2][8] = "DB@E@ICH@@EIH@ADB@@H@@@@@@@@@@D@HE@@ID@@@@@@@@G@@FC@@H@ID@@EH@@B@EF@G@@@@@@IAD@@E";
        solutions[2][8] = "DBFEGICHAGEIHCADBFCHABDFGEIFACDIHEGBIDHGEBFACEGBAFCIDHAIDCBEHFGBCEFHGAIDHFGIADBCE";

        tasks[2][9] = "@E@I@C@B@BH@@G@EIF@@@@@B@@@A@@B@EC@@CBH@A@F@@@G@@@F@H@H@@G@@@F@@@G@C@B@@FA@@BH@@G";
        solutions[2][9] = "GEDIFCHBABHCAGDEIFIFAHEBGDCAIFBHECGDCBHDAGFEIDGECIFAHBHCBGDAIFEEDGFCIBAHFAIEBHDCG";

        tasks[3][0] = "H@I@@@@@G@@F@@@H@DB@@@DH@I@DE@H@@GBF@@@@ED@@I@@B@F@@@@I@@@@@@EH@A@@@C@@B@@D@@@@@A";
        solutions[3][0] = "HDIACEBFGECFIGBHADBGAFDHEICDECHIAGBFGFHBEDACIAIBCFGDHEIBGDAFCEHFAEGHCIDBCHDEBIFGA";

        tasks[3][1] = "@@F@D@@@@@A@@@CGE@G@CH@EA@FI@@G@DBH@@B@ICF@@@@G@@@H@@@@@@@@@@@A@@@@G@@C@@@B@@@ED@";
        solutions[3][1] = "EIFADGCBHBAHFICGEDGDCHBEAIFIFEGADBHCHBAICFDGECGDBEHFAIDCGEHBIFAFEIDGAHCBAHBCFIEDG";

        tasks[3][2] = "@F@B@E@@@H@@@@@FE@G@@@@FBAHC@@@@A@@F@@FE@@GD@@D@I@@@@@@A@@EBC@@@@CDA@I@@@@@@@@H@A";
        solutions[3][2] = "IFABHEDCGHCBAGDFEIGEDCIFBAHCBHGDAEIFAIFEBHGDCEDGIFCAHBFAIHEBCGDBHCDAGIFEDGEFCIHBA";

        tasks[3][3] = "@E@@@I@CG@@D@@B@@E@@CD@@@@@@@@@C@F@@@@IGDE@@B@H@F@@@@@@BH@@F@G@C@@E@@H@@@A@@@CE@@";
        solutions[3][3] = "HEBAFIDCGAGDCHBIFEIFCDEGBAHGDABCHFEIFCIGDEAHBBHEFIAGDCEBHIAFCGDCIFEGDHBADAGHBCEIF";

        tasks[3][4] = "AG@H@@@@@@@H@@FCD@@@@@@@@BHB@@@F@G@@@@FG@@@AB@@AC@H@I@I@@E@G@@@@@@@AI@G@@A@@@@E@@";
        solutions[3][4] = "AGDHCBIEFEBHAIFCDGFCIDGEABHBHEIFAGCDCIFGEDHABGDACBHFIEIFCEDGBHAHEBFAIDGCDAGBHCEFI";

        tasks[3][5] = "B@@@@CH@DH@E@@B@@@@D@FA@B@@@@B@@@G@@E@H@@@@@I@@@@F@@B@I@@@EA@D@F@@@BI@C@@@@C@@I@E";
        solutions[3][5] = "BAFEGCHIDHGEIDBCFACDIFAHBEGDCBAIEGHFEFHBCGDAIGIAHFDEBCIHCGEAFDBFEGDBIACHABDCHFIGE";

        tasks[3][6] = "@G@@@EA@@@@IG@@H@B@@@@C@@@@@@@@@@@B@BH@C@@@@E@@D@BHFA@@@HI@@GE@FI@@GD@@@@D@@@B@@@";
        solutions[3][6] = "DGCBHEAIFEFIGDAHCBHABFCIEDGIEFDAGCBHBHACIFDGEGCDEBHFAIABHIFCGEDFIEAGDBHCCDGHEBIFA";

        tasks[3][7] = "E@H@@@@@D@C@@@@B@EDF@@C@@@G@E@@@@@H@I@@GH@@@FF@@@@IC@@@G@F@@E@B@@F@A@@G@@@@@@B@@C";
        solutions[3][7] = "EIHBFGACDGCAHIDBFEDFBACEHIGAEDCBFGHIIBCGHADEFFHGDEICBACGIFDHEABBDFEACIGHHAEIGBFDC";

        tasks[3][8] = "@@H@@IC@@D@@@A@@@G@@@@G@HF@B@AF@C@D@E@@@B@@@@@@@A@@@C@F@EGC@DH@@@@@@@E@@CH@E@@@B@";
        solutions[3][8] = "GFHBDICEADECHAFBIGAIBCGEHFDBGAFHCIDEECFIBDAGHHDIAEGFCBFAEGCBDHIIBGDFHEACCHDEIAGBF";

        tasks[3][9] = "A@H@@@@@@@@@@@@@@CDG@@@B@I@@@I@A@@C@@@A@F@D@EG@@B@@F@@@E@AG@CBH@@@@@H@F@@A@@IF@D@";
        solutions[3][9] = "ABHFCIGEDFIEGDABHCDGCHEBAIFEFIDAGHCBBHAIFCDGEGCDBHEFAIIEFAGDCBHCDGEBHIFAHABCIFEDG";

        tasks[4][0] = "@@@@@@@@@@@@@@@BGI@@@CAE@@@@@@@@@@@@@@B@@@EC@@@AGF@@@H@@G@@@@@@@B@@@@AH@@E@AH@F@C";
        solutions[4][0] = "HDFBGICAECAEHDFBGIBGICAEHDFDIHECAGFBGFBDIHECAECAGFBDIHAHGFBCIEDFBCIEDAHGIEDAHGFBC";
        
        tasks[4][1] = "@@@@@@@@@@@@@@@ICE@@@FBC@D@@@@@@@@@@@@@@@@GAF@@D@FB@@C@@H@A@@@G@GB@D@@I@@AC@@IHE@";
        solutions[4][1] = "CHAIEDFGBDBFAHGICEGEIFBCADHHFGECADBIBCEDIHGAFAIDGFBEHCIDHCAEBFGEGBHDFCIAFACBGIHED";

        tasks[4][2] = "@@@@@@@@@@@@@@@GIH@@@CIB@F@@@@@@@@@@@@@@@@BEF@@@HEI@D@@BI@@A@@@@@F@@G@@A@CE@BDI@@";
        solutions[4][2] = "EIDGFHCABBFCDAEGIHHAGCIBDFECEABDFHGIIDHAGCBEFFGBHEIADCGBIEHAFCDDHFICGEBAACEFBDIHG";

        tasks[4][3] = "@@@@@@@@@@@@@@@GHI@@@CHA@E@@@@@@@@@@@@@@@@BFE@@@BCEH@G@FG@@H@@B@I@@G@@@C@ED@A@I@@";
        solutions[4][3] = "FDHGEICBAACEDBFGHIIGBCHADEFEBIHFGACDGHCAIDBFEDAFBCEHIGCFGIDHEABHIAEGBFDCBEDFACIGH";

        tasks[4][4] = "@@@@@@@@@@@@@E@@CB@@@H@@DG@@@I@@@@@@@@E@@C@@F@@D@@@BHC@A@@@F@@@@F@@A@GED@EB@CGI@@";
        solutions[4][4] = "EBHCGDFAIGDAFEIHCBCIFHBADGEFCIAHBEDGBHEGDCAIFAGDIFEBHCDAGEIFCBHIFCBAHGEDHEBDCGIFA";

        tasks[4][5] = "@@@@@@@@@@@@@@@CDG@@@G@D@HA@@F@@@@@@@@D@@HE@@@@B@@F@@D@A@@C@@I@@BH@FA@G@@FC@@E@@B";
        solutions[4][5] = "GDABHCIEFBHEFAICDGFCIGEDBHACIFEDGABHAGDCBHEFIHEBAIFGCDDAGHCBFIEEBHIFADGCIFCDGEHAB";

        tasks[4][6] = "@@@@@@@@@@@@@@EG@D@@@GA@I@F@@B@@@@@@@@D@@C@H@@@I@@@C@G@DG@F@@BH@BH@@GE@@@F@@HB@@C";
        solutions[4][6] = "DGEICFHABAIFHBEGCDBHCGADIEFHCBDGAFIEGEDFICBHAFAIBEHCDGEDGCFIABHCBHADGEFIIFAEHBDGC";

        tasks[4][7] = "@@@@@@@@@@@@@@@HCB@@@@@@@ED@@E@@D@@I@@C@@A@@@@@A@@EBH@@AD@EF@@@@CF@AB@@G@EH@DGI@A";
        solutions[4][7] = "EHBDGCAIFADGEFIHCBCFIABHGEDHBEGCDFAIFICBHADGEDGAFIEBHCGADIEFCBHICFHABEDGBEHCDGIFA";

        tasks[4][8] = "@@@@@@@@@@@@@D@@AH@@@@@FC@D@@H@@@@@@@@C@@@@@@@@D@B@FEI@F@@ED@B@@G@@@C@@F@B@@A@@CG";
        solutions[4][8] = "ADGHCBIFEICFEDGBAHEHBAIFCGDBEHIFAGDCFICDGEAHBGADCBHFEICFIGEDHBADGABHCEIFHBEFAIDCG";

        tasks[4][9] = "@@@@@@@@@@@@@@@DBF@@@EIG@@@@@@@@@@G@@@@@@@F@C@E@AC@@@H@HG@@@@@E@@@@G@@I@@ADC@B@@@";
        solutions[4][9] = "HCABFDGEIEGIHACDBFBDFEIGCHAAFCDHIEGBDIHGBEFACGEBACFIDHFHGIDABCECBEFGHAIDIADCEBHFG";
    }

    public int[][] generateTask(int difficulty) {
        int[][] task = new int[9][9];
        int index = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                char cur = tasks[difficulty][version].charAt(index++);
                if (cur != '@') {
                    task[x][y] = converter[cur - 'A'];
                } else {
                    task[x][y] = 0;
                }
            }
        }
        task = rotate(task, timesRotate);
        task = mirror(task, timesMirror);
        return task;
    }

    public int[][] generateSolution(int difficulty) {
        int[][] solution = new int[9][9];
        int index = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                char cur = solutions[difficulty][version].charAt(index++);
                if (cur != '@') {
                    solution[x][y] = converter[cur - 'A'];
                } else {
                    solution[x][y] = 0;
                }
            }
        }
        solution = rotate(solution, timesRotate);
        solution = mirror(solution, timesMirror);
        return solution;
    }

    private int[][] rotate(int[][] grid) {
        int[][] updated = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                updated[x][y] = grid[y][8 - x];
            }
        }
        return updated;
    }

    private int[][] rotate(int[][] grid, int times) {
        int[][] updated = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                updated[x][y] = grid[x][y];
            }
        }
        for (int i = 0; i < times; i++) {
            updated = rotate(updated);
        }
        return updated;
    }

    private int[][] mirror(int[][] grid) {
        int[][] updated = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                updated[x][y] = grid[x][8 - y];
            }
        }
        return updated;
    }

    private int[][] mirror(int[][] grid, int times) {
        int[][] updated = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                updated[x][y] = grid[x][y];
            }
        }
        for (int i = 0; i < times; i++) {
            updated = mirror(updated);
        }
        return updated;
    }

    private int[] generateConverter() {
        ArrayList<Integer> leftNumbers = new ArrayList<>();
        int[] converter = new int[9];
        int index = 0;
        for (int i = 1; i <= 9; i++) {
            leftNumbers.add(i);
        }
        Random random = new Random();
        for (int i = 1; i <= 9; i++) {
            int randomNum = random.nextInt(leftNumbers.size());
            converter[index++] = leftNumbers.remove(randomNum);
        }
        return converter;
    }
}