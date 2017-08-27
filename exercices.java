import java.awt.Color;
 
import javax.swing.JPanel;
import javax.swing.JTextArea;
 
public class Panneau extends JPanel {
  private String texte = "Racine de l'arbre.";
  private JTextArea jta;
     
  public Panneau(){
    this.jta = new JTextArea(texte);
    this.setBackground(Color.white);
    this.add(jta);
  }
  public void setTexte(String texte) {
    this.jta.setText(texte);
  }
}




import java.awt.BorderLayout;
import java.io.File;
 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
 
public class Fenetre extends JFrame {
 
  private JTree arbre;
  private DefaultMutableTreeNode racine;
  private Panneau panneau = new Panneau();
 
  public Fenetre(){
    this.setSize(500, 200);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Les arbres");
    //On invoque la méthode de construction de notre arbre
    listRoot();
 
    this.setVisible(true);
  }
 
  private void listRoot(){
 
    this.racine = new DefaultMutableTreeNode();
 
    int count = 0;
    for(File file : File.listRoots())
    {
      DefaultMutableTreeNode lecteur = new DefaultMutableTreeNode(file.getAbsolutePath());
      try {
        for(File nom : file.listFiles()){
          DefaultMutableTreeNode node = new DefaultMutableTreeNode(nom.getName()+"\\");
          lecteur.add(this.listFile(nom, node));
        }
      } catch (NullPointerException e) {}
 
      this.racine.add(lecteur);
             
      //Si nous avons parcouru plus de 50 dossiers, on sort
      //if(count > 50) {break;}
             
    }
    //On crée, avec notre hiérarchie, un arbre
    arbre = new JTree(this.racine);
    arbre.setRootVisible(false);
    arbre.addTreeSelectionListener(new TreeSelectionListener(){
      public void valueChanged(TreeSelectionEvent event) {
        if(arbre.getLastSelectedPathComponent() != null){
          //La méthode getPath retourne un objet TreePath
          File file= new File(getAbsolutePath(event.getPath()));
          panneau.setTexte(getDescription(file));
        }
      }
 
      private String getAbsolutePath(TreePath treePath){
        String str = "";
        //On balaie le contenu de notre TreePath
        for(Object name : treePath.getPath()){
          //Si l'objet à un nom, on l'ajoute au chemin
          if(name.toString() != null)
            str += name.toString();
        }
        return str;
      }
 
      /**
      * Retourne une description d'un objet File
      * @param file
      * @return
      */
      private String getDescription(File file){
        String str = "Chemin d'accès sur le disque : \n\t" + file.getAbsolutePath() + "\n";
        str += (file.isFile()) ? "Je suis un fichier.\nJe fais " + file.length() + " ko\n" : "Je suis un dossier.\n";
        str += "J'ai des droits : \n";
        str += "\t en lecture : " + ((file.canRead()) ? "Oui;" : "Non;");
        str += "\n\t en écriture : " + ((file.canWrite()) ? "Oui;" : "Non;");
        return str;
      }
    });
    //On crée un séparateur de conteneur pour réviser
    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(arbre), new JScrollPane(panneau));
    //On place le séparateur
    split.setDividerLocation(200);
    //On ajoute le tout
    this.getContentPane().add(split, BorderLayout.CENTER);
  }
     
  private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node){
    int count = 0;
 
    if(file.isFile())
      return new DefaultMutableTreeNode(file.getName());
    else{
      File[] list = file.listFiles();
      if(list == null)
        return new DefaultMutableTreeNode(file.getName()); 
      for(File nom : file.listFiles()){
        count++;
        //Pas plus de 5 enfants par nœud
        if(count < 5){
          DefaultMutableTreeNode subNode;
          if(nom.isDirectory()){
            subNode = new DefaultMutableTreeNode(nom.getName()+"\\");
            node.add(this.listFile(nom, subNode));
          }else{
            subNode = new DefaultMutableTreeNode(nom.getName());
          }
          node.add(subNode);
        }
      }
      return node;
    }
  }
 
  public static void main(String[] args){
    Fenetre fen = new Fenetre();
  }
}



Fenetre.java: 
//CTRL + SHIFT + O pour générer les imports
public class Fenetre extends JFrame {
 
   private JTable tableau;
   private JButton change = new JButton("Changer la taille");
   private String[] comboData = {"Très bien", "Bien", "Mal"};
   private String supp = "Supprimer la ligne";
   private JComboBox combo;
    
   public Fenetre(){
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("JTable");
      this.setSize(600, 250);
      this.createContent();
   }
    
   private void createContent(){
      //Données de notre tableau
      Object[][] data = {  
         {"Cysboy", "6boy", comboData[0], new Boolean(true), supp},
         {"BZHHydde", "BZH", comboData[0], new Boolean(false), supp},
         {"IamBow", "BoW", comboData[0], new Boolean(false), supp},
         {"FunMan", "Year", comboData[0], new Boolean(true), supp}
      };
 
      String  title[] = {"Pseudo", "Age", "Taille", "OK ?", "Suppression"};
      combo = new JComboBox(comboData);
       
      //Notre modèle d'affichage spécifique destiné à pallier
      //les bugs d'affichage !
      ZModel zModel = new ZModel(data, title);
       
      this.tableau = new JTable(zModel);     
      this.tableau.setRowHeight(30);
      this.tableau.getColumn("Age").setCellRenderer(new ButtonRenderer());
      this.tableau.getColumn("Age").setCellEditor(new ButtonEditor(new JCheckBox()));
       
      //On définit l'éditeur par défaut pour la cellule
      //en lui spécifiant quel type d'affichage prendre en compte
      this.tableau.getColumn("Taille").setCellEditor(new DefaultCellEditor(combo));
      DefaultTableCellRenderer dcr = new DefaultTableCellRenderer();
      this.tableau.getColumn("Taille").setCellRenderer(dcr);
       
      //On définit un éditeur pour la colonne "supprimer"
      this.tableau.getColumn("Suppression").setCellEditor(new DeleteButtonEditor(new JCheckBox()));
       
      //On ajoute le tableau
      this.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
       
      JButton ajouter = new JButton("Ajouter une ligne");
      ajouter.addActionListener(new MoreListener());
      this.getContentPane().add(ajouter, BorderLayout.SOUTH);
   }     
    
   class MoreListener implements ActionListener{
      public void actionPerformed(ActionEvent event) {
         Object[] donnee = new Object[]
            {"Angelo", "Rennais", comboData[0], new Boolean(false), supp};
         ((ZModel)tableau.getModel()).addRow(donnee);
      }
   }
       
   public static void main(String[] args){
      Fenetre fen = new Fenetre();
      fen.setVisible(true);
   }
}



ZModel.java:
class ZModel extends AbstractTableModel{
 
   private Object[][] data;
   private String[] title;
    
   //Constructeur
   public ZModel(Object[][] data, String[] title){
      this.data = data;
      this.title = title;
   }
    
   //Retourne le titre de la colonne à l'indice spécifié
   public String getColumnName(int col) {
     return this.title[col];
   }
 
   //Retourne le nombre de colonnes
   public int getColumnCount() {
      return this.title.length;
   }
    
   //Retourne le nombre de lignes
   public int getRowCount() {
      return this.data.length;
   }
    
   //Retourne la valeur à l'emplacement spécifié
   public Object getValueAt(int row, int col) {
      return this.data[row][col];
   }
    
   //Définit la valeur à l'emplacement spécifié
   public void setValueAt(Object value, int row, int col) {
      //On interdit la modification sur certaines colonnes !
      if(!this.getColumnName(col).equals("Age")
         && !this.getColumnName(col).equals("Suppression"))
         this.data[row][col] = value;
   }
          
  //Retourne la classe de la donnée de la colonne
   public Class getColumnClass(int col){
      //On retourne le type de la cellule à la colonne demandée
      //On se moque de la ligne puisque les données sont les mêmes
      //On choisit donc la première ligne
      return this.data[0][col].getClass();
   }
 
   //Méthode permettant de retirer une ligne du tableau
   public void removeRow(int position){
       
      int indice = 0, indice2 = 0;
      nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
      Object temp[][] = new Object[nbRow][nbCol];
       
      for(Object[] value : this.data){
         if(indice != position){
            temp[indice2++] = value;
         }
         System.out.println("Indice = " + indice);
         indice++;
      }
      this.data = temp;
      temp = null;
      //Cette méthode permet d'avertir le tableau que les données
      //ont été modifiées, ce qui permet une mise à jour complète du tableau
      this.fireTableDataChanged();
   }
    
   //Permet d'ajouter une ligne dans le tableau
   public void addRow(Object[] data){
      int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
       
      Object temp[][] = this.data;
      this.data = new Object[nbRow+1][nbCol];
       
      for(Object[] value : temp)
         this.data[indice++] = value;
       
          
      this.data[indice] = data;
      temp = null;
      //Cette méthode permet d'avertir le tableau que les données
      //ont été modifiées, ce qui permet une mise à jour complète du tableau
      this.fireTableDataChanged();
   }
    
    
   public boolean isCellEditable(int row, int col){
      return true;
   }
}



ButtonRenderer.java:
//CTRL + SHIFT + O pour générer les imports
public class ButtonRenderer extends JButton implements TableCellRenderer{
 
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
      //On écrit dans le bouton avec la valeur de la cellule
      setText((value != null) ? value.toString() : "");
      //On retourne notre bouton
      return this;
   }
}



ButtonEditor.java:
//CTRL + SHIFT + O pour générer les imports
public class ButtonEditor extends DefaultCellEditor {
      
   protected JButton button;
   private ButtonListener bListener = new ButtonListener();
    
   public ButtonEditor(JCheckBox checkBox) {
      //Par défaut, ce type d'objet travaille avec un JCheckBox
      super(checkBox);
       //On crée à nouveau notre bouton
      button = new JButton();
       button.setOpaque(true);
       //On lui attribue un listener
       button.addActionListener(bListener);
   }
 
   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
      //On définit le numéro de ligne à notre listener
      bListener.setRow(row);
      //Idem pour le numéro de colonne
      bListener.setColumn(column);
      //On passe aussi en paramètre le tableau pour des actions potentielles
      bListener.setTable(table);
      //On réaffecte le libellé au bouton
      button.setText( (value ==null) ? "" : value.toString() );
      //On renvoie le bouton
       return button;
   }
    
   class ButtonListener implements ActionListener {
      
     private int column, row;
     private JTable table;
     private int nbre = 0;
     private JButton button;
      
     public void setColumn(int col){this.column = col;}
     public void setRow(int row){this.row = row;}
     public void setTable(JTable table){this.table = table;}
     public JButton getButton(){return this.button;}
      
     public void actionPerformed(ActionEvent event) {
      //On affiche un message mais vous pourriez faire ce que vous voulez
      System.out.println("coucou du bouton : "+
         ((JButton)event.getSource()).getText() );
      //On affecte un nouveau libellé à une celulle de la ligne
      ((AbstractTableModel)table.getModel()).setValueAt("New Value " + (++nbre), this.row, (this.column -1));  
      //Permet de dire à notre tableau qu'une valeur a changé
      //à l'emplacement déterminé par les valeurs passées en paramètres
      ((AbstractTableModel)table.getModel()).fireTableCellUpdated(this.row, this.column - 1);
      this.button = ((JButton)event.getSource());
     }
  }
}



DeleteButtonEditor.java:
//CTRL + SHIFT + O pour générer les imports
public class DeleteButtonEditor extends DefaultCellEditor {
      
   protected JButton button;
   private DeleteButtonListener bListener = new DeleteButtonListener();
    
   public DeleteButtonEditor(JCheckBox checkBox) {
      //Par défaut, ce type d'objet travaille avec un JCheckBox
      super(checkBox);
       //On crée à nouveau notre bouton
      button = new JButton();
       button.setOpaque(true);
       //On lui attribue un listener
       button.addActionListener(bListener);
   }
 
   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      //On définit le numéro de ligne à notre listener
      bListener.setRow(row);
      //On passe aussi en paramètre le tableau pour des actions potentielles
      bListener.setTable(table);
      //On réaffecte le libellé au bouton
      button.setText( (value ==null) ? "" : value.toString() );
      //On renvoie le bouton
       return button;
   }
    
   class DeleteButtonListener implements ActionListener {
         
        private int row;
        private JTable table;
         
        public void setRow(int row){this.row = row;}
        public void setTable(JTable table){this.table = table;}
         
        public void actionPerformed(ActionEvent event) {
         if(table.getRowCount() > 0){
            //On affiche un message mais vous pourriez faire ce que vous voulez
            System.out.println("coucou du bouton : "+ ((JButton)event.getSource()).getText() );
            //On affecte un nouveau libellé à une celulle de la ligne
            ((ZModel)table.getModel()).removeRow(this.row);
             
         }
      }
   }        
}



ComboRenderer.java:
//CTRL + SHIFT + O pour générer les imports
public class ComboRenderer extends JComboBox implements TableCellRenderer {
 
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
       
      this.addItem("Très bien");
      this.addItem("Bien");
      this.addItem("Mal");
      return this;




