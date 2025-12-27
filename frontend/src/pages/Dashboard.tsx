import React, { useEffect, useState } from 'react';
import { 
  Grid, 
  Paper, 
  Typography, 
  Card, 
  CardContent, 
  CardHeader,
  useTheme,
  makeStyles,
  Theme,
  createStyles
} from '@material-ui/core';
import {
  People as PeopleIcon,
  LocalHospital as HospitalIcon,
  EventAvailable as AppointmentIcon,
  Receipt as BillingIcon,
} from '@material-ui/icons';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
      padding: theme.spacing(3),
    },
    paper: {
      padding: theme.spacing(2),
      textAlign: 'center',
      color: theme.palette.text.secondary,
      height: '100%',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
    },
    card: {
      height: '100%',
      display: 'flex',
      flexDirection: 'column',
      transition: 'transform 0.2s',
      '&:hover': {
        transform: 'scale(1.03)',
      },
    },
    cardContent: {
      flexGrow: 1,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
    },
    icon: {
      fontSize: 48,
      marginBottom: theme.spacing(2),
    },
    title: {
      marginBottom: theme.spacing(3),
    },
  })
);

interface StatCardProps {
  title: string;
  value: string | number;
  icon: React.ReactNode;
  color: string;
}

const StatCard: React.FC<StatCardProps> = ({ title, value, icon, color }) => {
  const classes = useStyles();
  
  return (
    <Card className={classes.card}>
      <CardHeader 
        title={title} 
        titleTypographyProps={{ align: 'center' }}
      />
      <CardContent className={classes.cardContent}>
        <div style={{ color }}>
          {icon}
        </div>
        <Typography component="h3" variant="h3">
          {value}
        </Typography>
      </CardContent>
    </Card>
  );
};

const Dashboard: React.FC = () => {
  const classes = useStyles();
  const theme = useTheme();
  
  // Mock data - in a real app, this would come from an API
  const [stats, setStats] = useState({
    totalPatients: 0,
    activeAppointments: 0,
    totalDoctors: 0,
    monthlyRevenue: 0,
  });

  useEffect(() => {
    // Simulate loading data
    const timer = setTimeout(() => {
      setStats({
        totalPatients: 1245,
        activeAppointments: 28,
        totalDoctors: 42,
        monthlyRevenue: 125000,
      });
    }, 500);

    return () => clearTimeout(timer);
  }, []);

  return (
    <div className={classes.root}>
      <Typography variant="h4" className={classes.title}>
        Hospital Dashboard
      </Typography>
      
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Total Patients"
            value={stats.totalPatients.toLocaleString()}
            icon={<PeopleIcon className={classes.icon} />}
            color={theme.palette.primary.main}
          />
        </Grid>
        
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Active Appointments"
            value={stats.activeAppointments}
            icon={<AppointmentIcon className={classes.icon} />}
            color={theme.palette.secondary.main}
          />
        </Grid>
        
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Doctors"
            value={stats.totalDoctors}
            icon={<HospitalIcon className={classes.icon} />}
            color={theme.palette.success.main}
          />
        </Grid>
        
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Monthly Revenue"
            value={`$${stats.monthlyRevenue.toLocaleString()}`}
            icon={<BillingIcon className={classes.icon} />}
            color={theme.palette.warning.main}
          />
        </Grid>
        
        {/* Add more dashboard widgets here */}
      </Grid>
    </div>
  );
};

export default Dashboard;
