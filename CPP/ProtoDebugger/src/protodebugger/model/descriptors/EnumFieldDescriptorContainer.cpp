#include "EnumFieldDescriptorContainer.h"
#include <sstream>

using namespace std;
using namespace google;
using namespace protobuf;

QWidget * EnumFieldDescriptorContainer::getWidget(QWidget * parent)
{
    if(m_comboBox == NULL)
    {
        m_comboBox = new QComboBox(parent);

        if (m_type == FieldDescriptor::CPPTYPE_ENUM)
        {
            m_comboBox->addItems(getValues());

            if(m_defaultValue != NULL)
            {
                EnumValueDescriptor * value = (EnumValueDescriptor *) m_defaultValue;
                m_comboBox->setCurrentIndex(value->index());
            }
        }
    }
    else if (m_comboBox->parentWidget() != parent)
    {
        m_comboBox->setParent(parent);
    }

    return m_comboBox;
}

QWidget * EnumFieldDescriptorContainer::getParent()
{
    if(m_comboBox != NULL)
    {
       return m_comboBox->parentWidget();
    }

    return NULL;
}

Object * EnumFieldDescriptorContainer::getValue()
{
    stringstream ss;
    if(m_value != NULL)
    {
        if (m_type == FieldDescriptor::CPPTYPE_ENUM)
        {
            ss << m_value;
            return (Object *) ss.str().c_str();
        }
     }
    else if (m_defaultValue != NULL)
    {
        if (m_type == FieldDescriptor::CPPTYPE_ENUM)
        {
            EnumValueDescriptor * value = (EnumValueDescriptor *) m_defaultValue;
            return (Object *) value->name().c_str();
        }
    }
    return (Object *) m_field->enum_type()->value(0)->name().c_str();
}

void EnumFieldDescriptorContainer::setValue(Object * value)
{
    char * result = (char *) value;
    string check = result;
    if(check.c_str() != NULL)
    {
        for (int i = 0; i < m_field->enum_type()->value_count(); ++i)
        {
            if(m_field->enum_type()->name().compare(check) == 0)
            {
                m_value = (Object *)m_field->enum_type();
                return;
            }
        }
    }
}

QString EnumFieldDescriptorContainer::toString()
{
    string str = "EnumField name = " + this->m_name;
    return QString(str.c_str());
}

QStringList EnumFieldDescriptorContainer::getValues()
{
    int arraySize = m_field->enum_type()->value_count();
    if (m_field->is_optional())
    {
        arraySize += 1;
    }
    QStringList result;

    for (int i = 0; i < arraySize; ++i)
    {
        result << m_field->enum_type()->value(i)->name().c_str();
    }
    if(m_field->is_optional())
    {
        result << "";
    }
    return result;
}

bool EnumFieldDescriptorContainer::buildMsg(Message *msg)
{
    if( m_field->is_optional() && m_comboBox->currentText().compare("") == 0)
    {
        return false;
    }
    if(m_field->is_repeated())
    {
        int index = 0;
        QStringList check = this->getValues();

        for(int i = 0; i < m_field->enum_type()->value_count(); ++i)
        {
            if( m_field->enum_type()->value(i)->name().compare(check.at(i).toStdString()) == 0)
            {
                index = i;
                break;
            }
        }
        msg->GetReflection()->AddEnum(msg, m_field, m_field->enum_type()->value(index));
    }
    else
    {
        int index = 0;
        QStringList check = this->getValues();

        for(int i = 0; i < m_field->enum_type()->value_count(); ++i)
        {
            if( m_field->enum_type()->value(i)->name().compare(check.at(i).toStdString()) == 0)
            {
                index = i;
                break;
            }
        }
        msg->GetReflection()->SetEnum(msg, m_field, m_field->enum_type()->value(index));
    }
    return true;
}
