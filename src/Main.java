import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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
        CursoRepositoryImpl cursoRepository = new CursoRepositoryImpl();
        PPCRepositoryImpl ppcRepository = new PPCRepositoryImpl();
        LogRepositoryImpl logRepository = new LogRepositoryImpl();

        CursoService cursoService = new CursoService(cursoRepository);
        PPCService ppcService = new PPCService(ppcRepository);
        MatriculaService matriculaService = new MatriculaService(usuarioRepository, ppcService);
        AuthService authService = new AuthService(usuarioRepository);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository, matriculaService);
        GrupoService grupoService = new GrupoService(grupoRepository, logRepository, usuarioRepository);
        CertificadoService certificadoService = new CertificadoService(certificadoRepository, oportunidadeRepository, usuarioRepository);
        OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository, inscricoesRepository, matriculaService, certificadoService);
        AproveitamentoService aproveitamentoService = new AproveitamentoService(aproveitamentoRepository);
        InscricaoService inscricaoService = new InscricaoService(inscricoesRepository, oportunidadeService);
        Scanner scanner = new Scanner(System.in);


        //Dia Atual
        LocalDate data = LocalDate.now();


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
                        TelaPrincipal(authService, oportunidadeService, aproveitamentoService, grupoService, inscricaoService, usuarioService, ppcService, data);
                    } catch (RuntimeException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case "2":
                    Cadastro(usuarioService, cursoService);
                    break;
                case "3":
                    System.out.println("Data atual: " + data);
                    System.out.println("1 - Pular um dia");
                    System.out.println("2 - Pular um mês");
                    System.out.println("3 - Pular um ano");
                    System.out.println("0 - Voltar");

                    String opc3 = scanner.nextLine();

                    switch (opc3) {
                        case "1":
                            data = data.plusDays(1);
                            System.out.println("Nova data: " + data);
                            break;
                        case "2":
                            data = data.plusMonths(1);
                            System.out.println("Nova data: " + data);
                            break;
                        case "3":
                            data = data.plusYears(1);
                            System.out.println("Nova data: " + data);
                            break;
                        case "0":
                            break;
                        default:
                            System.out.println("Opção inválida");
                    }
                    break;
                case "0":
                    System.out.println("Encerrando...");
                    return;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public static void Cadastro(UsuarioService usuarioService, CursoService cursoService) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Senha:");
        String senha = scanner.nextLine();




        System.out.println("Digite (1 - Discente / 2 - Docente):");
        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }

        switch (opc) {
            case 1:
                System.out.println("Semestre:");
                int sem;
                try {
                    sem = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Semestre inválido.");
                    return;
                }

                List<Curso> cursos = cursoService.listarTodos();
                if (cursos.isEmpty()) {
                    System.out.println("Nenhum curso cadastrado.");
                    return;
                }

                System.out.println("\nCursos disponíveis:");
                for (int i = 0; i < cursos.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + cursos.get(i).getNome());
                }

                System.out.println("Escolha o número do curso:");
                int opcCurso;
                try {
                    opcCurso = Integer.parseInt(scanner.nextLine());
                    if (opcCurso < 1 || opcCurso > cursos.size()) {
                        System.out.println("Opção inválida.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida.");
                    return;
                }

                Curso curso = cursos.get(opcCurso - 1);
                usuarioService.cadastrarDiscente(nome, email, senha, sem, curso);
                System.out.println("Discente cadastrado com sucesso!");
                break;


            case 2:
                System.out.println("Siape:");
                String siape = scanner.nextLine();

                System.out.println("Departamento:");
                String dep = scanner.nextLine();

                usuarioService.cadastrarDocente(nome, email, senha, siape, dep);
                System.out.println("Docente cadastrado com sucesso!");
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
            UsuarioService usuarioService,
            PPCService ppcService,
            LocalDate data
    ) {
        Usuario usuario = authService.getUsuarioLogado();

        Tela tela;

        if (usuario instanceof DiscenteDiretor dd) {
            tela = new TelaDiscenteDiretor(
                    oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService, dd, ppcService, data);

        } else if (usuario instanceof Discente d) {
            tela = new TelaDiscente(
                    oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService, d, ppcService, data);

        }else if (usuario instanceof Coordenador coo){
            tela = new TelaCoordenador(oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService,ppcService,coo, data);

        } else if (usuario instanceof Docente doc) {
            tela = new TelaDocente(
                    oportunidadeService, aproveitamentoService,
                    inscricaoService, grupoService, usuarioService, ppcService, doc, data);

        } else {
            System.out.println("Tipo de usuário não reconhecido.");
            return;
        }
        oportunidadeService.verificarOportunidadesExpiradas(data);
        oportunidadeService.finalizarOportunidades();
        tela.mostrarTela();

        authService.logout();
        System.out.println("Sessão encerrada.");
    }
}
