# Restaurant

<b>STORY</b><br>
A local restaurant needs a system for placing orders.<br>
Every table has it's own window, where people can order anything to eat from the menu.
<br>

<b>Entities</b><br>
<ul>
  <li>Table</li>
  <ul>
    <li>ID</li>
  </ul>
  
  <li>Order</li>
  <ul>
    <li>ID</li>
    <li>Table ID</li>
    <li>Items (Selected Food)</li>
    <li>Date</li>
    <li>Status</li>
  </ul>
  
  <li>Menu Item</li>
  <ul>
    <li>ID</li>
    <li>Category</li>
    <li>Item</li>
    <li>Price</li>
    <li>Currency</li>
  </ul>
</ul>

<b>Features</b>
<ul>

<li>Tables and menu's items are read from a database, you can not add any of them in the app</li>
<li>There's a staff window, which shows two tables : Placed Orders and Preparing Orders</li>
<li>Every table displays the menu divided in the categories found in the database</li>
  <ul>
  Explanation: You read every menu's item from the database. The category field is a string, so you can have a large amount of categories.<br>
  Every category has its own table. 
  </ul>
<li>People can choose(multiple choices in multiple tables) and order what they want to eat from their table's window</li>
<li>After an order is placed, it is visible immediately on the staff's window (in Placed Orders table)</li>
<li>Staff can change status of an order (placed orders can change their status into preparing orders, preparing orders can change their status to delivered orders)</li>
<li>When an order's status is changed, an information window is shown immediately with the title set as the table's name (eg. 'Table 1', 'Table 100')</li>
<li>An order can have just unique items (people can not order 3 x Pasta, they have to make multiple orders to achieve that)</li>

</ul>

