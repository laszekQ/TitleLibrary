#include "mainwindow.h"
#include <QCoreApplication>
#include <QSqlDatabase>
#include <QSqlQuery>
#include <QSqlError>
#include <QDebug>
#include <qtableview.h>
#include <QSqlTableModel>

#ifndef DATABASE_H
#define DATABASE_H

QSqlDatabase InitializeDatabase();
void InsertTitle(const QString &name, int grade, const QString &desc);
QSqlQueryModel* SetupSelectModel(MainWindow *w, QTableView *tableView);
void RefreshData(QSqlQueryModel *model);

#endif // DATABASE_H
