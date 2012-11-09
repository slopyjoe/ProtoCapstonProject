#include "TextFieldDescriptorContainer.h"
#include <sstream>

using namespace std;
using namespace google;
using namespace protobuf;

QWidget * TextFieldDescriptorContainer::getWidget(QWidget * parent)
{
    stringstream ss;
    if(m_textField == NULL)
    {
        m_textField = new QLineEdit(parent);

        ss << m_value;
        m_textField->setText(QString::fromStdString(ss.str()));
    }
    else if (m_textField->parentWidget() != parent)
    {
        m_textField->setParent(parent);
    }

    return m_textField;
}

QWidget * TextFieldDescriptorContainer::getParent()
{
    if(m_textField != NULL)
    {
       return m_textField->parentWidget();
    }

    return NULL;
}

Object * TextFieldDescriptorContainer::getValue()
{
    stringstream ss;
    if( m_value != NULL)
    {
        ss << m_value;
        return (Object *) ss.str().c_str();
    }
    else if (m_defaultValue != NULL)
    {
        ss << m_defaultValue;
        return (Object *) ss.str().c_str();
    }
    return (Object *) "";
}

void TextFieldDescriptorContainer::setValue(Object * value)
{
    m_value = value;
}

QString TextFieldDescriptorContainer::toString()
{
    string str = "TextField name = " + this->m_name;
    return QString(str.c_str());
}

bool TextFieldDescriptorContainer::buildMsg(Message *msg)
{
    if(m_field->is_optional() && m_textField->text().compare("") == 0)
    {
        return false;
    }
    if (m_type == FieldDescriptor::CPPTYPE_STRING)
    {
        if(m_field->is_repeated())
        {
             msg->GetReflection()->AddString(msg, m_field, m_textField->text().toStdString());
        }
        else
        {
            msg->GetReflection()->SetString(msg, m_field, m_textField->text().toStdString());
        }
    }
    return true;
}

