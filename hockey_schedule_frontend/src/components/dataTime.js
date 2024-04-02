import * as React from 'react';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';


//Принимаем onDateChange в качестве пропа
export function BasicDatePicker({ onDateChange }) {
    const handleChange = (date) => {
      onDateChange(date); // Передаем выбранную дату обратно в родительский компонент
    };
  
    return (
     <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DemoContainer components={['DatePicker']}>
          <DatePicker label="Game date" onChange={handleChange} format="DD/MM/YYYY"/>
        </DemoContainer>
      </LocalizationProvider>  
    );
  }
  
  // Принимаем onTimeChange в качестве пропа
  export function BasicTimePicker({ onTimeChange }) {
    const handleChange = (time) => {
        const newTime = {
            hours: time.hour(),
            minutes: time.minute()
        };
      onTimeChange(time); // Передаем выбранное время обратно в родительский компонент
    };
  
    return (
      <LocalizationProvider dateAdapter={AdapterDayjs} >
        <DemoContainer components={['TimePicker']}>
          <TimePicker label="Game time" onChange={handleChange} ampm={false} format='HH:mm' />
        </DemoContainer>
      </LocalizationProvider>
    );
  }

// export function BasicDatePicker() {
//   return (
//     <LocalizationProvider dateAdapter={AdapterDayjs}>
//       <DemoContainer components={['DatePicker']}>
//         <DatePicker label="Basic date picker" />
//       </DemoContainer>
//     </LocalizationProvider>
//   );
// }

// export function BasicTimePicker() {
//   return (
//     <LocalizationProvider dateAdapter={AdapterDayjs}>
//       <DemoContainer components={['TimePicker']}>
//         <TimePicker label="Basic time picker" />
//       </DemoContainer>
//     </LocalizationProvider>
//   );
// }

