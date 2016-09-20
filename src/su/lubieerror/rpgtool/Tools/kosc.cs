
        public Form1() 
        {
            sec = DateTime.Now.Second;
            minuty = DateTime.Now.Minute;
            godziny = DateTime.Now.Hour;
        }
        
        
        int sec, minuty, godziny = -1;
        int count;

        private void startButton_Click(object sender, EventArgs e) 
        {
            timer1.Interval = 250;
            sec = DateTime.Now.Second;
            timer1.Start();
        }

        private void timer1_Tick(object sender, EventArgs e) 
        {
            if (sec != DateTime.Now.Second) 
            {
                count++;
                sec = DateTime.Now.Second;
                TimeSpan time = TimeSpan.FromSeconds(count);
                label7.Text = time.ToString(@"hh\:mm\.ss");
            }
        }

        private void pauseButton_Click(object sender, EventArgs e) 
        {
            timer1.Stop();
        }

        private void stopButton_Click(object sender, EventArgs e) 
        {
            timer1.Stop();
            count = 0;
            label7.Text = "00:00.00";
        }

