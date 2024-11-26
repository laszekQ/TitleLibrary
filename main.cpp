#include "mainwindow.h"
#include "Database.h"

#include <QApplication>
#include <QLocale>
#include <QTranslator>

#include <QSqlDatabase>
#include <QSqlQuery>
#include <QSqlError>
#include <qheaderview.h>
#include <qmenu.h>
#include <qmenubar.h>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QTranslator translator;
    const QStringList uiLanguages = QLocale::system().uiLanguages();
    for (const QString &locale : uiLanguages)
    {
        const QString baseName = "TitleLibrary_" + QLocale(locale).name();
        if (translator.load(":/i18n/" + baseName))
        {
            a.installTranslator(&translator);
            break;
        }
    }

    MainWindow w;
    QTableView *tableV = w.findChild<QTableView *>("titlesTableView");

    QSqlDatabase db = InitializeDatabase();
    QSqlQueryModel *model = SetupSelectModel(&w, tableV);

    w.SetQueryModel(model);
    RefreshData(model);

    QItemSelectionModel *selectionModel = w.findChild<QTableView *>("titlesTableView")->selectionModel();
    MainWindow::connect(selectionModel, &QItemSelectionModel::selectionChanged, &w, &MainWindow::OnRowSelection);
    MainWindow::connect(tableV->horizontalHeader(), &QHeaderView::sectionClicked, &w, &MainWindow::OnHeaderSelection);

    w.show();
    return a.exec();
}
