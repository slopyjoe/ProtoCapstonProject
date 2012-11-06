#ifndef FIELDDESCRIPTORCONTAINER_H
#define FIELDDESCRIPTORCONTAINER_H

#include <QtGui/QWidget>
#include <QString>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/message.h>
#include <google/protobuf/generated_message_util.h>

template<class Object>
class FieldDescriptorContainer
{
    protected:
        bool m_subfield;
        FieldDescriptorContainer * m_parent;
        int m_type;
        Object * m_value;
        Object * m_defaultValue;

    public:
        std::string m_name;
        google::protobuf::FieldDescriptor  * m_field;
        virtual QWidget * getWidget(QWidget *parent = 0) = 0;
        virtual QWidget * getParent() = 0;
        virtual Object * getValue() = 0;
        virtual void setValue(Object * value) = 0;
        virtual QString toString() = 0;
        virtual bool buildMsg(google::protobuf::Message * msg) = 0;

        FieldDescriptorContainer(google::protobuf::FieldDescriptor * field)
        {
            m_field = field;
            m_name = field->name();
            m_type = field->cpp_type();

            //Set the default value depending on type
            if(field->has_default_value())
            {
                switch(field->cpp_type())
                {
                    case google::protobuf::FieldDescriptor::CPPTYPE_BOOL:
                    {
                        m_defaultValue = (Object *)field->default_value_bool();
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_DOUBLE:
                    {
                        //Note: not sure if this really works.  Had to unbox the value from a double into a long
                        //before converting into the Object type.
                        m_defaultValue = (Object *) (long) field->default_value_double();
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_ENUM:
                    {
                         m_defaultValue =  (Object *)field->default_value_enum();
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_FLOAT:
                    {
                        //Note: not sure if this really works.  Had to unbox the value from a float into a int
                        //before converting into the Object type.
                        m_defaultValue = (Object *) (int) field->default_value_float();
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_INT32:
                    {
                        m_defaultValue = (Object *)field->default_value_int32();
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_INT64:
                    {
                        m_defaultValue = (Object *)field->default_value_int64();
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_MESSAGE:
                    {
                        m_defaultValue = NULL;
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_STRING:
                    {
                        m_defaultValue = (Object *)field->default_value_string().c_str();
                        break;
                    }
                    case google::protobuf::FieldDescriptor::CPPTYPE_UINT32:
                    {
                        m_defaultValue = (Object *)field->default_value_uint32();
                        break;
                    }
                    default:
                    {
                        m_defaultValue = (Object *)field->default_value_uint64();
                        break;
                    }
                }
            }
        }

        //Destructor
        ~FieldDescriptorContainer()
        {
            delete m_field;
            delete &m_value;
            delete &m_defaultValue;
        }

        bool isSubField()
        {
            return m_subfield;
        }

        void setSubfield(bool sub)
        {
            m_subfield = sub;
        }

        void setFieldParent(FieldDescriptorContainer * parent)
        {
            this->parent = parent;
        }

        FieldDescriptorContainer * getFieldParent()
        {
            if(m_subfield)
            {
             return m_parent;
            }
            return NULL;
        }
};

#endif // FIELDDESCRIPTORCONTAINER_H
