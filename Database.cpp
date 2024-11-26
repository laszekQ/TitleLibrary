#include "mainwindow.h"
#include <QCoreApplication>
#include <QSqlDatabase>
#include <QSqlQuery>
#include <QSqlError>
#include <QDebug>
#include <qtableview.h>
#include <QSqlTableModel>
#include "Database.h"

QSqlDatabase InitializeDatabase()
{
    QSqlDatabase db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName("libraryDB.db");

    if (!db.open())
    {
        qDebug() << "Error: Could not open or create database:" << db.lastError().text();
        return db;
    }

    QSqlQuery query;
    QString createTable = "CREATE TABLE IF NOT EXISTS library ("
                          "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                          "name TEXT NOT NULL, "
                          "grade INTEGER, "
                          "desc TEXT, "
                          "type TEXT, "
                          "image TEXT)";
    if (!query.exec(createTable))
        qDebug() << "Error: Failed to open or create table:" << query.lastError().text();

    return db;
}

/*
void InsertTitle(const QString &name, int grade, const QString &desc) {
    QSqlQuery query;
    query.prepare("INSERT INTO library (name, grade, desc) VALUES (:name, :value, :desc)");
    query.bindValue(":name", name);
    query.bindValue(":value", grade);
    query.bindValue(":description", desc);

    if (!query.exec()) {
        qDebug() << "Error: Failed to insert new data:" << query.lastError().text();
    }
    return;
}
*/

QSqlQueryModel* SetupSelectModel(MainWindow *w, QTableView *tableView)
{
    QSqlQueryModel  *model = new QSqlQueryModel(w);
    model->setQuery("SELECT name AS Name, grade AS Grade, desc AS Description, type AS Type FROM library");
    if (model->lastError().isValid())
    {
        qDebug() << "Error: Failed to execute query:" << model->lastError().text();
        return model;
    }

    tableView->setModel(model);
    return model;
}

void RefreshData(QSqlQueryModel *model)
{
    model->clear();
    model->setQuery("SELECT name AS Name, grade AS Grade, desc AS Description, type AS Type FROM library");
    return;
}
