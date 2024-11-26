#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <qitemselectionmodel.h>
#include <qsqlquerymodel.h>

QT_BEGIN_NAMESPACE
namespace Ui {
class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
    void SetQueryModel(QSqlQueryModel *m);
    void OnRowSelection();
    void OnHeaderSelection(int column);

private:
    Ui::MainWindow *ui;
    QSqlQueryModel *model;
    void AddTitle();
    void RemoveTitle();
    void SendAddQuery();
    void CallOpenFile();
};
#endif // MAINWINDOW_H
