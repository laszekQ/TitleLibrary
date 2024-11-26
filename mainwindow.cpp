#include "mainwindow.h"
#include "./ui_mainwindow.h"
#include "Database.h"

#include <QGraphicsView>
#include <QGraphicsScene>
#include <QGraphicsPixmapItem>
#include <QFileDialog>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    this->setFixedSize(800, 600);

    QPushButton *applyButton = ui->applyButton;
    QPushButton *coverButton = ui->chooseCoverButton;

    QMenu *addMenu = ui->menuAdd;
    QAction *addAction = new QAction(tr("Add"), this);
    QAction *removeAction = new QAction(tr("Remove"), this);

    connect(addAction, &QAction::triggered, this, &MainWindow::AddTitle);
    connect(removeAction, &QAction::triggered, this, &MainWindow::RemoveTitle);

    connect(applyButton, &QPushButton::clicked, this, &MainWindow::SendAddQuery);
    connect(coverButton, &QPushButton::clicked, this, &MainWindow::CallOpenFile);

    addMenu->addAction(addAction);
    addMenu->addAction(removeAction);

    ui->titlesTableView->setSelectionMode(QAbstractItemView::SingleSelection);
    ui->titlesTableView->setSelectionBehavior(QAbstractItemView::SelectRows);

    ui->coverFileLabel->setVisible(false);
    ui->idLabel->setVisible(false);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::SetQueryModel(QSqlQueryModel *m)
{
    this->model = m;
    return;
}

void MainWindow::AddTitle()
{
    QGraphicsView *imageGraphV = ui->imageGraphicsView;
    QLineEdit *nameLineE = ui->nameLineEdit;
    QSpinBox *gradeSpinBox = ui->gradeSpinBox;
    QTextEdit *descTextEdit = ui->descTextEdit;
    QPushButton *applyButton = ui->applyButton;
    QComboBox *typeComboB = ui->typeComboBox;
    QPushButton *coverButton = ui->chooseCoverButton;

    imageGraphV->setEnabled(true);
    nameLineE->setEnabled(true);
    gradeSpinBox->setEnabled(true);
    descTextEdit->setEnabled(true);
    applyButton->setEnabled(true);
    typeComboB->setEnabled(true);
    coverButton->setEnabled(true);

    return;
}

void MainWindow::RemoveTitle()
{
    QTableView *tableV = ui->titlesTableView;
    QItemSelectionModel *selectionModel = tableV->selectionModel();
    QModelIndexList selectedIndexes = selectionModel->selectedIndexes();

    int row = -1;
    if (!selectedIndexes.isEmpty())
    {
        QSqlQuery query;
        query.prepare("DELETE FROM library WHERE id = :id");

        QString id = ui->idLabel->text();
        //QVariant grade = this->model->data(this->model->index(row, 0));
        //QVariant desc = this->model->data(this->model->index(row, 0));
        //QVariant name = model->data(model->index(row, 0));

        query.bindValue(":id", id);
        //query.bindValue(":grade", grade);
        //query.bindValue(":description", desc);
        //query.bindValue(":img", img);

        if (!query.exec())
        {
            qDebug() << "Error: Failed to delete data:" << query.lastError().text();
        }
        RefreshData(this->model);
    }

    return;
}

void MainWindow::SendAddQuery()
{
    QString name, grade, desc, type, img;

    QGraphicsView *imageGraphV = ui->imageGraphicsView;
    QLabel *imageLabel = ui->coverFileLabel;
    QLineEdit *nameLineE = ui->nameLineEdit;
    QSpinBox *gradeSpinBox = ui->gradeSpinBox;
    QTextEdit *descTextEdit = ui->descTextEdit;
    QPushButton *applyButton = ui->applyButton;
    QComboBox *typeComboB = ui->typeComboBox;
    QPushButton *coverButton = ui->chooseCoverButton;

    name = nameLineE->text();
    grade = gradeSpinBox->text();
    desc = descTextEdit->toPlainText();
    type = typeComboB->currentText();
    img = imageLabel->text();

    QSqlQuery query;
    query.prepare("INSERT INTO library (name, grade, desc, type, image) VALUES (:name, :grade, :desc, :type, :img)");
    query.bindValue(":name", name);
    query.bindValue(":grade", grade);
    query.bindValue(":desc", desc);
    query.bindValue(":type", type);
    query.bindValue(":img", img);

    if (!query.exec())
    {
        qDebug() << "Error: Failed to insert new data:" << query.lastError().text();
    }

    RefreshData(this->model);

    imageGraphV->setEnabled(false);
    nameLineE->setEnabled(false);
    gradeSpinBox->setEnabled(false);
    descTextEdit->setEnabled(false);
    applyButton->setEnabled(false);
    typeComboB->setEnabled(false);
    coverButton->setEnabled(false);

    return;
}

void MainWindow::OnRowSelection()
{
    QLabel *idLabel = ui->idLabel;

    QTableView *tableV = ui->titlesTableView;
    QItemSelectionModel *selected = tableV->selectionModel();

    int row = -1;
    if (selected->hasSelection())
    {
        row = selected->selectedIndexes().first().row();
    }
    else
        return;

    QGraphicsView *imageGraphV = ui->imageGraphicsView;
    QLineEdit *nameLineE = ui->nameLineEdit;
    QSpinBox *gradeSpinBox = ui->gradeSpinBox;
    QTextEdit *descTextEdit = ui->descTextEdit;
    QPushButton *applyButton = ui->applyButton;
    QComboBox *typeComboB = ui->typeComboBox;
    QPushButton *coverButton = ui->chooseCoverButton;
    QLabel *imageLabel = ui->coverFileLabel;

    QVariant name = this->model->data(this->model->index(row, 0));
    QVariant grade = this->model->data(this->model->index(row, 1));
    QVariant desc = this->model->data(this->model->index(row, 2));
    QVariant type = this->model->data(this->model->index(row, 3));

    nameLineE->setText(name.toString());
    gradeSpinBox->setValue(grade.toInt());
    descTextEdit->setText(desc.toString());
    typeComboB->setCurrentText(type.toString());

    //dealing with the cover
    QSqlQuery getCoverQuery;
    getCoverQuery.prepare("SELECT image, id FROM library WHERE name = :name");
    getCoverQuery.bindValue(":name", name.toString());

    if(getCoverQuery.exec())
    {
        getCoverQuery.next();
        QString fileName = getCoverQuery.value(0).toString();
        imageLabel->setText(fileName);
        //qDebug() << fileName;

        QPixmap pixmap(fileName);
        if (!pixmap.isNull())
        {
            QSize viewSize = ui->imageGraphicsView->size();
            pixmap = pixmap.scaled(viewSize, Qt::KeepAspectRatio, Qt::SmoothTransformation);

            QGraphicsScene *scene = new QGraphicsScene();
            QGraphicsPixmapItem *item = new QGraphicsPixmapItem(pixmap);
            scene->addItem(item);
            ui->imageGraphicsView->setScene(scene);
        }
        else
            qDebug() << "Failed to load the image.";

        QString curId = getCoverQuery.value(1).toString();
        idLabel->setText(curId);
    }
    else
        qDebug("Couldn't get the cover path.");

    //----------------------

    imageGraphV->setEnabled(true);
    nameLineE->setEnabled(true);
    gradeSpinBox->setEnabled(true);
    descTextEdit->setEnabled(true);
    applyButton->setEnabled(true);
    typeComboB->setEnabled(true);
    coverButton->setEnabled(true);

    return;
}

void MainWindow::OnHeaderSelection(int column)
{
    /*
    QItemSelectionModel *selected = ui->titlesTableView->selectionModel();
    int column = -1;

    if(selected->hasSelection() && selected->selectedColumns().count() == 1)
        column = selected->selectedColumns().first().column();
    else
        return;
    */

    QString columnName = model->headerData(column, Qt::Horizontal).toString();

    QString query;
    query = ("SELECT name AS Name, grade AS Grade, desc AS Description, type AS Type FROM library ORDER BY ");

    columnName = columnName.toLower();
    columnName = (column == 2) ? "desc" : columnName;

    query += columnName + " DESC";

    model->setQuery(query);

    return;
}

void MainWindow::CallOpenFile()
{
    QString fileName = QFileDialog::getOpenFileName(nullptr,
            "Choose title cover",
            "C:/",
            "Images (*.png *.jpg);;All files (*)");

    if (!fileName.isEmpty())
    {
        ui->coverFileLabel->setText(fileName);
        QPixmap pixmap(fileName);

        if (!pixmap.isNull())
        {
            QSize viewSize = ui->imageGraphicsView->size();
            pixmap = pixmap.scaled(viewSize, Qt::KeepAspectRatio, Qt::SmoothTransformation);

            QGraphicsScene *scene = new QGraphicsScene();
            QGraphicsPixmapItem *item = new QGraphicsPixmapItem(pixmap);
            scene->addItem(item);
            ui->imageGraphicsView->setScene(scene);
        }
        else
            qDebug() << "Failed to load the image.";
    }

    return;
}
