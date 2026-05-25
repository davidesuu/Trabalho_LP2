import java.io.IOException;
import java.util.Scanner;

import Entity.*;
import Repository.impl.*;
import Service.*;
import Telas.*;

public class Main {

//            ⢿⢻⢻⣿⡟⠻⠻⣿⡿⠻⠟⣿⣿⠟⢿⣿⣿⢿⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⡿⠋⠀⠙
//            ⣾⣸⣾⣿⣦⡀⣠⣿⣧⣀⣠⣿⣿⣄⣼⣿⣯⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡴⠋⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⡖⠋⣩⣄⠀⠀⢀
//            ⢿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡸⠋⠀⠀⠀⠀⠀⠀⢀⣀⡤⠾⠟⠋⠀⠀⠈⠋⠀⠀⠙
//            ⣾⣿⣿⣿⣷⣤⣼⣿⣿⣬⣽⣿⣿⣤⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡤⠶⠛⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⣿⣿⣿⣿⣿⣏⣿⣿⣿⣿⣻⡿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣀⣤⣤⣤⢤⣶⣶⣶⣾⣿⣿
//            ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠈⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
//            ⣏⣿⣿⣿⣟⣁⣹⣿⡟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠑⣌⣿⣿⣭⠁⣹⣿⣁⡁⣿
//            ⣿⣿⣿⣿⣿⢿⣿⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⡿⣿⣿
//            ⣼⣹⣿⣿⣇⣰⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣉⣉⣽⣿⡀⣀⣿
//            ⢿⢻⣿⣿⣿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡟⣿⣿⣿⣿⣿⣿
//            ⣾⣼⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡞⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢷⣘⣿⣿⣆⣁⣿
//            ⢿⣿⣿⣿⠁⠀⠔⠛⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢯⣻⣿⣿⣿⣿
//            ⣾⣾⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿
//            ⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⣾⣿⣝⠛⢦⡾⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣿⣿
//            ⣿⣿⣿⣷⠀⠀⣠⣶⣶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⡇⠈⣿⠋⠀⠀⠀⢀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣿⣿⣿
//            ⣏⣽⣿⣿⣄⣨⣿⣿⣿⠉⢷⡀⠀⠀⠀⠀⠀⠀⠀⣹⣯⣈⣽⣿⣷⠀⠈⢀⣠⡤⠒⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣫⣏⣽
//            ⣿⣿⣿⣿⣷⢿⣿⣿⣿⣆⣼⠷⠛⠙⡛⠛⠒⠀⠀⠿⠿⠛⠉⠉⠀⠀⠴⠟⠉⢀⣠⣴⣤⠤⠤⠤⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿
//            ⣼⣽⣿⣿⣿⠈⠛⠿⢿⡏⠀⣴⣟⢉⣹⠆⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠴⠚⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣏⣿
//            ⢿⢻⣿⡟⠁⠀⠀⠀⢸⠀⠀⠈⠉⠛⠃⠀⠀⠀⠀⠛⣶⣄⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⣿⣿⣿⣿
//            ⣾⣾⣿⣧⠄⠀⠀⠀⠸⣄⣀⣀⣠⣶⣦⣤⣤⣤⡤⠞⡁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣏⣤⣿⣿⣧⣅⣿
//            ⢿⣿⣿⣧⣴⠂⠀⠀⠀⠀⠉⠉⠁⠉⠻⠿⠿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣶⣿⣿⣿⣍⣿⣿⡿⣿⣿
//            ⣿⣼⣿⣿⣿⣦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣤⣴⣾⣿⣿⣶⣿⣿⣿⣯⣭⣿⣿⣯⣿⣿
//            ⡿⣿⣿⣿⣏⣤⣽⣿⣶⣤⣄⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠒⠛⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉
//            ⣿⣿⣿⣿⣿⡿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⣿⣹⣿⣿⣏⣀⣭⣿⣿⣀⣩⣿⣿⣅⣸⣿⣿⠤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠉⠉⠛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⢦⡀⠀⠀⠉⠻⣿⣿⣽⣽⣿⡿⠛⢉⣾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣤⣤⣤⣤⣤⣤⣀⣀
//            ⠀⠈⠁⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⢼⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡏⠀⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⣽⣿⣿⣿⣿⣿⣿
//            ⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣷⠀⠀⠑⢤⣸⣦⣀⣀⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠻⢿⣿⣿⣿⣿
//            ⣿⣿⣷⣶⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⡿⠀⠀⠀⠀⠈⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠻⢿
//            ⣏⣿⣿⣿⣿⣭⣿⣿⣷⣶⣦⣤⣤⢴⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⣿⣿⣿⣿⣿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⣫⣽⣿⣿⣧⣀⣨⣿⣿⣮⣨⣿⣿⣄⣹⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⡿⠿⠿⣿⡿⠿⢿⣿⡿⠿⢿⣿⡿⠿⠿⣷⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//
//    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣤⣤⣤⣤⣴⣶⣶⣶⣿⣿⣿⣿⣿⣷⣶⣶⣶⣦⣤⣤⣄⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣤⣴⣶⡶⣿⠿⠿⠟⠛⠛⠉⠉⢉⠉⠉⠁⠀⢀⣄⣀⠀⠉⣉⣉⠉⠉⠛⠛⠻⠿⢿⣿⣿⣿⣷⣶⣤⡀⠀⠀⠀⠀⠀
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣴⡶⠾⠟⠋⠉⠉⢁⣤⣴⣶⣶⠟⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⣼⣿⣿⠀⢈⣿⣿⠀⢠⣶⣶⣶⣶⣦⣤⣈⠙⠻⢿⣿⣿⣷⣦⡀⠀⠀
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣤⠶⠞⠋⠉⢁⣼⣷⠂⠀⠀⠀⠀⣾⣿⣿⣿⡟⠀⠀⠀⠀⠀⢀⣾⣷⣤⣤⠖⢻⣿⣿⡄⠠⣿⣿⠀⣿⣿⣿⡏⠀⠉⠙⠻⣿⣶⣤⡈⠻⣿⣿⣿⣦⠀
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⠶⠚⣛⡉⠀⠀⠀⠀⢠⣿⣿⠃⠀⠀⠀⠀⠀⣿⣿⣿⣿⠃⠀⠀⠀⣀⣴⣿⣿⣿⡿⠁⠀⢸⣿⣿⣷⠀⣿⣿⠀⢿⣿⣿⣷⣄⠀⠀⠀⠈⢻⣿⣿⣦⠈⢻⣿⣿⡆
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡤⠾⠛⠉⠀⠀⣴⣿⠁⠀⣴⡆⠀⣸⣿⡟⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⢀⣠⠴⠛⠉⠁⠘⠉⢻⡇⠀⠀⠀⣿⣿⣿⣇⢹⣿⣇⠈⢻⣿⣿⣿⣿⣦⣄⠀⠈⠿⠿⠛⠛⠉⢻⣿⣿
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡴⠟⠉⢀⣴⣶⣦⡀⠀⣿⣿⠀⢰⣿⡇⠀⣿⣿⠃⠀⠀⠀⠀⠀⢸⣿⣿⣿⡏⠉⠀⠀⢀⣴⣶⣆⠀⠈⣧⠀⠀⠀⢹⣿⣎⣿⣿⣿⣿⣧⡀⠉⠻⣿⣿⣿⣿⣿⣶⣄⠀⠀⠀⠀⢸⣿⡟
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡴⢻⣽⡆⠀⢠⣾⣿⣿⣿⣷⠀⣿⣿⡄⢸⣿⣿⢠⣿⣿⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⠃⠀⠀⢠⣿⣿⣿⣿⠀⠀⠙⠀⠀⠀⠈⣿⣿⡍⢿⣿⣿⣿⣿⣦⣄⡀⠉⠛⢿⣿⣿⣿⣷⡀⠀⠀⣸⣿⠃
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠟⢩⣶⣿⣿⠃⠀⣾⣿⣿⣿⣿⣿⠀⣿⣿⡆⢸⣿⢿⣾⣿⡇⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⠀⠀⢀⣿⡿⣿⣿⡿⠀⠀⠀⣀⣤⣀⠀⢸⣿⣷⡀⠹⣿⠿⠛⠉⣉⣤⠀⠀⠀⠙⢿⣿⣿⡇⠐⢰⣿⠋⠀
//            ⠀⠀⠀⠀⠀⠀⠀⣠⡾⣿⠀⠀⢸⣿⣿⣿⠀⢀⣿⡿⠁⢻⣿⣿⠃⢼⣿⡇⢸⣿⢸⣿⣿⡇⠀⠀⠀⢀⠀⠀⠰⣿⣿⣿⠀⠀⢸⡿⠀⣿⣿⠃⠀⢀⣿⣿⠿⣿⣧⠐⢿⣿⣿⣄⠀⠰⣶⣿⣿⣿⣆⠀⠀⠀⢘⣿⣿⡇⢸⡿⠁⠀⠀
//            ⠀⠀⠀⠀⠀⣠⣾⣫⣿⣿⠀⠀⢸⣿⣿⣿⡀⢸⣿⡇⠀⠀⣿⡿⠀⢸⣿⣷⣼⣿⠀⢛⡁⢀⣠⣴⣾⣿⡀⠀⠀⣿⣿⣿⠀⠀⣿⡇⢠⣿⡟⠀⠀⣼⣿⡏⠀⢸⣿⡆⠈⢿⣿⣿⣦⡀⠉⠿⣿⣿⣿⣷⣤⣤⣾⣿⣿⣶⠟⠀⠀⠀⠀
//            ⠀⠀⠀⢠⡾⢋⣽⣿⣿⣿⡀⠀⠈⣿⣿⣿⡇⢸⣿⡇⠀⢀⣿⠇⠀⠸⣿⣿⣿⣿⠀⠸⣿⣿⣿⣿⣿⣿⣇⠀⠀⢸⣿⣿⡄⠀⣿⣷⣿⠟⣠⠆⢠⣿⣿⠇⠀⣸⣿⡇⠀⠈⢿⣿⣿⣿⣄⡀⠈⠙⠛⠿⠿⣿⣟⣿⠟⠁⠀⠀⠀⠀⠀
//            ⠀⠀⣰⡟⠁⢸⣿⣿⣿⣿⣧⠀⠀⣿⣿⣿⣧⠀⣿⣇⠀⣼⡟⠀⣰⡇⢿⣿⣿⣿⡆⠀⢻⣿⣿⣿⣿⣿⣿⡄⠀⠀⣿⣿⡇⠀⣿⡿⠃⣼⣿⠀⢸⡿⠟⠁⣰⣿⣿⣇⠀⠀⠈⣿⣿⣿⣿⣿⣶⣄⣄⠀⣠⡼⠋⠁⠀⠀⠀⠀⠀⠀⠀
//            ⠀⣼⡿⠀⠀⢸⣿⣿⣿⣿⣿⡄⠀⢸⣿⣿⣿⠀⢻⣿⣼⡿⠉⣼⣿⣇⠘⣿⣿⣿⣧⠀⠈⣿⣿⣿⣿⣿⣿⣷⠀⠀⢸⣿⣷⢀⣿⡅⢸⣿⣿⠂⠈⠁⣠⣾⡿⠃⣿⣿⣄⣀⣤⣍⠿⣿⣿⣿⣿⣻⡧⠞⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⢸⣿⡇⠀⠀⠘⣿⣿⣿⣿⣽⣿⡀⠀⣿⣿⣿⡆⠘⣿⡟⠁⠸⣿⣿⣿⡀⢹⣿⣿⣿⡆⠀⠹⣿⣿⣿⣿⣿⣿⣧⠀⠀⣿⣿⣾⣿⣇⢸⣿⣿⠀⢀⣾⣿⠟⠀⢠⣿⣿⣿⣿⣿⣿⡶⡽⣿⣶⠾⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⣿⣿⡇⠀⠀⠀⢿⣿⣿⣿⣷⢻⣷⡄⢸⣿⣿⣷⠀⢻⣷⡀⠀⣿⣿⣿⡇⠀⣿⣿⣿⣿⡀⠀⠹⣿⣿⣿⣿⣿⣿⣷⣤⣾⣿⡧⢸⣿⣿⣿⣿⢀⣿⣿⠁⠀⣠⣿⡟⠙⠛⢻⣿⣻⡶⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⣿⣿⣷⡀⠀⠀⠘⣿⣿⣿⣿⣇⠹⣿⡄⢻⣿⣿⣧⠈⢿⣷⣤⣾⣿⣿⣷⠀⠘⣿⣿⠿⠓⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠈⢿⣿⣿⡇⠈⢿⣿⣿⣿⣿⠋⣀⣠⡴⠟⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠹⣿⣿⣷⣄⠀⠀⠹⣿⣿⣿⣿⣧⠘⢿⣾⣿⣿⣿⣆⠈⢿⣿⣿⣿⣿⣿⠀⠀⠙⠁⠀⠀⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⡟⠀⠘⠿⠟⠀⠀⠀⣁⣨⣤⠿⠚⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⠘⢿⣿⣿⣷⣄⡀⠙⠿⣿⣿⣿⣧⡀⠹⣿⣿⣿⣿⣦⠈⠻⣿⣿⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⠿⢿⢿⠿⠋⠀⢀⣀⣀⣤⠶⠶⠛⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⠀⠀⠉⠻⣿⣿⣿⣷⣦⣤⣉⡙⠻⠷⠤⠀⠹⢿⣿⣿⣷⣄⠈⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣤⣤⣷⠶⠞⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⠀⠀⠀⠀⠀⠈⠙⠛⠿⢿⣿⣿⣿⣿⣶⣶⣶⣦⣶⣤⣬⣽⣦⣤⣤⣤⣤⣤⣤⣤⣤⣴⣶⣶⠶⠾⠟⠛⠋⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠙⠛⠛⠛⠛⠛⠛⠛⠛⠛⠋⠉⠉⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀

    public static void main(String[] args) throws IOException {

        //Repositorios
        OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
        InscricoesRepositoryImpl inscricoesRepository = new InscricoesRepositoryImpl();
        UsuarioRepositoryImpl usuarioRepository = new UsuarioRepositoryImpl();
        GrupoRepositoryImpl grupoRepository = new GrupoRepositoryImpl();
        AproveitamentoRepositoryImpl aproveitamentoRepository = new AproveitamentoRepositoryImpl();
        CertificadoRepositoryImpl certificadoRepository = new CertificadoRepositoryImpl();

        //Services
        AuthService authService = new AuthService(usuarioRepository);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        GrupoService grupoService = new GrupoService(grupoRepository);
        OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
        AproveitamentoService aproveitamentoService = new AproveitamentoService(aproveitamentoRepository);
        InscricaoService inscricaoService = new InscricaoService(inscricoesRepository);
        CertificadoService certificadoService = new CertificadoService(certificadoRepository);

        // Usuários de teste
        Curso curso = new Curso("Ciência da Computação", 123320);
        // Oportunidade oportunidade = oportunidadeService.criarOportunidade("titulo", "descriçao", TipoOportunidade.CURSO, Modalidade.HIBRIDO, 10, 10, usuarioService.getId(2L));
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBEM VINDO");
            System.out.println("1 - Login");
            System.out.println("2 - Cadastro");
            System.out.println("3 - Menu secreto");
            System.out.println("0 - Sair");

            String opc = scanner.nextLine();

            switch (opc) {
                case "1":
                    try {
                        Login(authService);
                        TelaPrincipal(authService, oportunidadeService, aproveitamentoService, grupoService, inscricaoService, usuarioService);
                    } catch (RuntimeException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case "2":
                    Cadastro(usuarioService);
                    break;
                case "3":
                    // certificadoService.criarCertificado(oportunidade, (Discente) usuarioService.getId(2L), 1);
                    break;
                case "0":
                    System.out.println("Encerrando...");
                    return;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public static void Cadastro(UsuarioService usuarioService) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Senha:");
        String senha = scanner.nextLine();

        System.out.println("Tipo (Discente/Docente/DiscDiretor):");
        String opc = scanner.nextLine();

        Curso curso = new Curso("ccomp", 123);

        switch (opc) {
            case "Discente":
                System.out.println("Matricula:");
                String mat = scanner.nextLine();

                System.out.println("Semestre:");
                int sem = scanner.nextInt();
                scanner.nextLine();

                usuarioService.cadastrarDiscente(nome, email, senha, mat, sem, curso);
                break;

            case "Docente":
                System.out.println("Siape:");
                String siape = scanner.nextLine();

                System.out.println("Departamento:");
                String dep = scanner.nextLine();

                usuarioService.cadastrarDocente(nome, email, senha, siape, dep);
                break;

            default:
                System.out.println("Tipo inválido");
        }
    }

    public static Usuario Login(AuthService authService) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Senha:");
        String senha = scanner.nextLine();

        Usuario u = authService.login(email, senha);


        return u;
    }

    public static void TelaPrincipal(
            AuthService authService,
            OportunidadeService oportunidadeService,
            AproveitamentoService aproveitamentoService,
            GrupoService grupoService,
            InscricaoService inscricaoService,
            UsuarioService usuarioService
    ) {
        Usuario usuario = authService.getUsuarioLogado();

        Tela tela;

        if (usuario instanceof DiscenteDiretor dd) {
            tela = new TelaDiscenteDiretor(
                    oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService, dd);

        } else if (usuario instanceof Discente d) {
            tela = new TelaDiscente(
                    oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService, d);

        }else if (usuario instanceof Coordenador coo){
            tela = new TelaCoordenador(oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService, coo);

        } else if (usuario instanceof Docente doc) {
            tela = new TelaDocente(
                    oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService, doc);

        } else {
            System.out.println("Tipo de usuário não reconhecido.");
            return;
        }

        tela.mostrarTela();

        authService.logout();
        System.out.println("Sessão encerrada.");
    }
}
