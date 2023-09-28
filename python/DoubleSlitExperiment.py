import tkinter as tk
from tkinter import ttk
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

def calculate_intensity():
    d = float(edit1.get())
    D = float(edit2.get())
    wavelength = float(edit3.get())
    frequency = float(edit4.get())
    I0 = 1.0

    delta_x = D * wavelength / d

    # To refresh and redraw the plot window 
    # when new parameters are entered and the "Calculate" button is clicked,
    # just add following codes and delete similar codes in GUI part.
    figure = plt.figure(figsize=(6,4), dpi=100)
    axes1 = figure.add_subplot(121)
    axes2 = figure.add_subplot(122)
    canvas = FigureCanvasTkAgg(figure, master=root)
    canvas.get_tk_widget().grid(column=0,row=1,columnspan=4,rowspan=4,padx=10,pady=10)

    x = np.arange(-4*delta_x, 4*delta_x, delta_x/frequency)
    intensity1 = 4 * I0 * np.cos(np.pi * d * x / (wavelength * D)) * np.cos(np.pi * d * x / (wavelength * D))

    axes1.clear()
    axes1.plot(x, intensity1)
    axes1.set(xlabel='x/m', ylabel='Intensity', title='Interference Fringe Intensity')
    canvas.draw()

    x, y = np.meshgrid(x, x)
    intensity2 = 4 * I0 * np.cos(np.pi * d * x / (wavelength * D)) * np.cos(np.pi * d * x / (wavelength * D))

    axes2.clear()
    axes2.imshow(intensity2, cmap='gray')
    axes2.set(xticks=[], yticks=[], title='Interference Fringe Pattern')
    colorbar = plt.colorbar(axes2.imshow(intensity2, cmap='gray'), ax=axes2)
    colorbar.set_label('Intensity')
    canvas.draw()

root = tk.Tk()
root.title('Interference Fringe Calculator')

mainframe = ttk.Frame(root, padding='3 3 12 12')
mainframe.grid(column=0, row=0, sticky=(tk.N, tk.W, tk.E, tk.S))
mainframe.columnconfigure(0, weight=1)
mainframe.rowconfigure(0, weight=1)

edit1 = ttk.Entry(mainframe, width=7)
edit1.grid(column=2, row=1, sticky=(tk.W, tk.E))

edit2 = ttk.Entry(mainframe, width=7)
edit2.grid(column=2, row=2, sticky=(tk.W, tk.E))

edit3 = ttk.Entry(mainframe, width=7)
edit3.grid(column=2, row=3, sticky=(tk.W, tk.E))

edit4 = ttk.Entry(mainframe, width=7)
edit4.grid(column=2, row=4, sticky=(tk.W, tk.E))

ttk.Label(mainframe, text='Distance (d):').grid(column=1, row=1, sticky=tk.W)
ttk.Label(mainframe, text='Screen Distance (D):').grid(column=1, row=2, sticky=tk.W)
ttk.Label(mainframe, text='Wavelength (\u03BB):').grid(column=1, row=3, sticky=tk.W)
ttk.Label(mainframe, text='Frequency:').grid(column=1, row=4, sticky=tk.W)

ttk.Button(mainframe, text='Calculate', command=calculate_intensity).grid(column=3, row=1, sticky=tk.W)

for child in mainframe.winfo_children():
    child.grid_configure(padx=5, pady=5)

root.mainloop()
