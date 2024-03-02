//// Создание интерполятора при запуске программы
//Interpolator interpolator = new Interpolator();
//
//// Добавление точек данных в интерполятор
//        interpolator.addPoint(x1, y1);
//        interpolator.addPoint(x2, y2);
//// добавление других точек...
//
//// Запрос значения в определенной точке
//        double x = 2.5; // Например, значение X, для которого вы хотите найти соответствующее значение Y
//        double interpolatedY = interpolator.interpolate(x);
//
//        // Создаем панель для мини-окна
//        JPanel miniWindowPanel = new JPanel();
//        miniWindowPanel.setLayout(new BoxLayout(miniWindowPanel, BoxLayout.Y_AXIS)); // Размещаем компоненты вертикально
//
//// Создаем мини-окно и добавляем его на панель
//        CoordinateInfoWindow miniWindow = new CoordinateInfoWindow(xCoordinate, calculateY(xCoordinate), e.getX(), e.getY(), panel);
//        miniWindowPanel.add(miniWindow);
//
//// Добавляем панель с мини-окном в правую часть основной панели
//        panel.add(miniWindowPanel, BorderLayout.EAST);
